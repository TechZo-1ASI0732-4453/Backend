package com.techzo.cambiazo.exchanges.domain.ports;

import com.techzo.cambiazo.exchanges.application.internal.services.ExchangeAiService.ProductSuggestion;

public interface AiSuggestionPort {

    ProductSuggestion suggestAllFromImage(byte[] image, String mimeType);
}
