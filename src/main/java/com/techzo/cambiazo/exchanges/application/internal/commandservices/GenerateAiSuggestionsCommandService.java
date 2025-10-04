package com.techzo.cambiazo.exchanges.application.internal.commandservices;

import com.techzo.cambiazo.exchanges.application.internal.services.ExchangeAiService;
import org.springframework.stereotype.Service;

@Service
public class GenerateAiSuggestionsCommandService {

    private final ExchangeAiService exchangeAiService;

    public GenerateAiSuggestionsCommandService(ExchangeAiService exchangeAiService) {
        this.exchangeAiService = exchangeAiService;
    }

    public ExchangeAiService.ProductSuggestion suggestFromImage(byte[] image, String mimeType) {
        return exchangeAiService.suggestAllFromImage(image, mimeType);
    }
}
