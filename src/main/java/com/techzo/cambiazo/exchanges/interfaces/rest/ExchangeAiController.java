package com.techzo.cambiazo.exchanges.interfaces.rest;

import com.techzo.cambiazo.exchanges.application.internal.services.ExchangeAiService;
import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ProductSuggestionResource;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
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
    public ProductSuggestionResource suggestFromImage(@RequestParam("file") MultipartFile file) throws Exception {
        String ct = file.getContentType() != null ? file.getContentType() : MediaType.IMAGE_JPEG_VALUE;
        return aiService.suggestAllFromImage(file.getBytes(), ct);
    }
}
