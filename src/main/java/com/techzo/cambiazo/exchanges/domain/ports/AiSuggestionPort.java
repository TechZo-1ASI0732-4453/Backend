package com.techzo.cambiazo.exchanges.domain.ports;

import com.techzo.cambiazo.exchanges.interfaces.rest.resources.ProductSuggestionResource;

public interface AiSuggestionPort {
    ProductSuggestionResource suggestAllFromImage(byte[] image, String mimeType);
}