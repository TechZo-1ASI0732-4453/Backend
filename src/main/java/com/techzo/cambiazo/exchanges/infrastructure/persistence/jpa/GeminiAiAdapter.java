package com.techzo.cambiazo.exchanges.infrastructure.persistence.jpa;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techzo.cambiazo.exchanges.application.internal.services.ExchangeAiService.ProductSuggestion;
import com.techzo.cambiazo.exchanges.domain.ports.AiSuggestionPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.text.Normalizer;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GeminiAiAdapter implements AiSuggestionPort {

    private final WebClient webClient;
    private final IProductCategoryRepository categoryRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private static final String MODEL_VISION = "models/gemini-2.0-flash";

    public GeminiAiAdapter(
            @Qualifier("geminiWebClient") WebClient geminiWebClient,
            IProductCategoryRepository categoryRepository) {
        this.webClient = geminiWebClient;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductSuggestion suggestAllFromImage(byte[] image, String mimeType) {
        List<String> categories = categoryRepository.findAll()
                .stream().map(c -> c.getName().trim()).distinct().sorted().toList();

        String raw = callGeminiVisionToJson(image, mimeType, categories);

        SuggestionPayload payload = parseSuggestionJson(raw);

        String price = normalizePrice(payload.price());

        String category = pickClosestCategory(payload.category(), categories);

        return new ProductSuggestion(
                emptyOr(payload.name(), "Producto"),
                emptyOr(payload.description(), "Sin descripción"),
                emptyOr(price, "0"),
                emptyOr(category, categories.isEmpty() ? "Sin categoría" : categories.get(0))
        );
    }

    private String callGeminiVisionToJson(byte[] image, String mimeType, List<String> categories) {
        String b64 = Base64.getEncoder().encodeToString(image);

        String categoriesInline = String.join("|", categories);

        String prompt = """
                Eres un asistente de catálogo. Analiza la imagen del producto y devuelve SOLO este JSON (sin texto extra):
                {"name":"...", "description":"...", "price":"...", "category":"..."}

                Reglas:
                - Español.
                - "price": solo número (ej: "129.90"), sin símbolo ni texto.
                - "category": escoge EXACTAMENTE una de esta lista: %s
                - "description": máx 25 palabras; informativa.
                - Si dudas, sé conservador.

                Ahora analiza la imagen.
                """.formatted(categoriesInline);

        var body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", prompt),
                                Map.of("inlineData", Map.of(
                                        "mimeType", mimeType,
                                        "data", b64
                                ))
                        ))
                )
        );

        try {
            return webClient.post()
                    .uri("/" + MODEL_VISION + ":generateContent?key=" + geminiApiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new IllegalStateException("Gemini vision error " + e.getStatusCode() + ": " + e.getResponseBodyAsString(), e);
        }
    }

    private record SuggestionPayload(String name, String description, String price, String category) {}

    private SuggestionPayload parseSuggestionJson(String rawGeminiResponse) {
        try {
            JsonNode root = mapper.readTree(rawGeminiResponse);
            JsonNode candidates = root.path("candidates");
            if (candidates.isArray() && candidates.size() > 0) {
                JsonNode parts = candidates.get(0).path("content").path("parts");
                for (JsonNode p : parts) {
                    if (p.has("text")) {
                        String json = p.get("text").asText().trim();
                        String cleaned = trimFence(json);
                        JsonNode obj = mapper.readTree(cleaned);
                        String name = optText(obj, "name");
                        String desc = optText(obj, "description");
                        String price = optText(obj, "price");
                        String category = optText(obj, "category");
                        return new SuggestionPayload(name, desc, price, category);
                    }
                }
            }
        } catch (Exception ignored) {}
        return new SuggestionPayload("", "", "", "");
    }

    private static String trimFence(String s) {
        String t = s.strip();
        if (t.startsWith("```")) {
            int idx = t.indexOf('\n');
            if (idx > 0) t = t.substring(idx + 1);
            if (t.endsWith("```")) t = t.substring(0, t.length() - 3);
        }
        return t.trim();
    }

    private static String optText(JsonNode obj, String field) {
        return obj.has(field) && !obj.get(field).isNull() ? obj.get(field).asText() : "";
    }

    private static String normalizePrice(String p) {
        if (p == null) return "0";
        String digits = p.replaceAll("[^0-9.,]", "").replace(",", ".");
        if (digits.endsWith(".")) digits = digits.substring(0, digits.length() - 1);
        return digits.isBlank() ? "0" : digits;
    }

    private static String emptyOr(String v, String fallback) {
        return (v == null || v.isBlank()) ? fallback : v.trim();
    }

    private static String normalize(String s) {
        String n = s == null ? "" : s.toLowerCase(Locale.ROOT).trim();
        n = Normalizer.normalize(n, Normalizer.Form.NFD).replaceAll("\\p{M}", ""); // quita acentos
        return n.replaceAll("\\s+", " ");
    }

    private static String pickClosestCategory(String predicted, List<String> categories) {
        if (categories == null || categories.isEmpty()) return predicted == null ? "" : predicted;

        String np = normalize(predicted);
        Map<String, String> normalizedMap = categories.stream()
                .collect(Collectors.toMap(GeminiAiAdapter::normalize, c -> c, (a, b) -> a));

        if (normalizedMap.containsKey(np)) return normalizedMap.get(np);

        for (var e : normalizedMap.entrySet()) {
            if (np.contains(e.getKey()) || e.getKey().contains(np)) {
                return e.getValue();
            }
        }

        String otros = categories.stream()
                .filter(c -> normalize(c).contains("otros") || normalize(c).contains("varios"))
                .findFirst().orElse(null);

        return otros != null ? otros : categories.get(0);
    }
}
