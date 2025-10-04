package com.techzo.cambiazo.exchanges.interfaces.rest;

import com.techzo.cambiazo.exchanges.application.internal.services.ExchangeAiService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/exchanges/ai")
@Tag(name = "AI", description = "Endpoints de IA para sugerencias de producto (por imagen)")
public class ExchangeAiController {

    private final ExchangeAiService aiService;

    public ExchangeAiController(ExchangeAiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping(value = "/suggest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeAiService.ProductSuggestion suggestFromImage(@RequestParam("file") MultipartFile file) throws Exception {
        return aiService.suggestAllFromImage(file.getBytes(), file.getContentType());
    }
}
