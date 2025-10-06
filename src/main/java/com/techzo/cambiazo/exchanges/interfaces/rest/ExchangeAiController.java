package com.techzo.cambiazo.exchanges.interfaces.rest;

import com.techzo.cambiazo.exchanges.application.internal.services.ExchangeAiService;
import com.techzo.cambiazo.exchanges.domain.exceptions.ContentViolationException;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ContentViolationResource;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ProductSuggestionResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v2/exchanges/ai")
@Tag(name = "AI", description = "AI endpoints for product suggestions (by image)")
public class ExchangeAiController {

    private final ExchangeAiService aiService;

    public ExchangeAiController(ExchangeAiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping(value="/suggest", consumes=MediaType.MULTIPART_FORM_DATA_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> suggestFromImage(@RequestParam("file") MultipartFile file) throws Exception {
        try {
            String ct = file.getContentType() != null ? file.getContentType() : MediaType.IMAGE_JPEG_VALUE;
            ProductSuggestionResource suggestion = aiService.suggestAllFromImage(file.getBytes(), ct);
            return ResponseEntity.ok(suggestion);
        } catch (ContentViolationException e) {
            ContentViolationResource violation = new ContentViolationResource(
                e.getViolationType().name(),
                e.getReason(),
                e.getBanDurationMinutes(),
                "Política de contenido de Cambiazo"
            );
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(violation);
        }
    }
}
