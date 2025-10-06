package com.techzo.cambiazo.exchanges.domain.exceptions;

import com.techzo.cambiazo.exchanges.domain.model.ContentViolationType;

public class ContentViolationException extends RuntimeException {
    private final ContentViolationType violationType;
    private final String reason;
    
    public ContentViolationException(ContentViolationType violationType, String reason) {
        super(String.format("Content violation detected: %s - %s", violationType.getDescription(), reason));
        this.violationType = violationType;
        this.reason = reason;
    }
    
    public ContentViolationType getViolationType() {
        return violationType;
    }
    
    public String getReason() {
        return reason;
    }
    
    public int getBanDurationMinutes() {
        return violationType.getBanDurationMinutes();
    }
}
