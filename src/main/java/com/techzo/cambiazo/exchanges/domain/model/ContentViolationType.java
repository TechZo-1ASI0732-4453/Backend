package com.techzo.cambiazo.exchanges.domain.model;

public enum ContentViolationType {
    SEXUAL_EXPLICIT(2, "Contenido sexual explícito"),
    WEAPONS_OR_DRUGS(2, "Armas o sustancias ilegales"),
    VIOLENCE(720, "Contenido violento"),
    PERSONAL_INFO(60, "Información personal sensible"),
    NONE(0, "Sin violaciones");
    
    private final int banDurationMinutes;
    private final String description;
    
    ContentViolationType(int banDurationMinutes, String description) {
        this.banDurationMinutes = banDurationMinutes;
        this.description = description;
    }
    
    public int getBanDurationMinutes() {
        return banDurationMinutes;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static ContentViolationType fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return NONE;
        }
        
        try {
            return ContentViolationType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return NONE;
        }
    }
}
