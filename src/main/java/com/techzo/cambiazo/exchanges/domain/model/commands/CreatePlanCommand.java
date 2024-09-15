package com.techzo.cambiazo.exchanges.domain.model.commands;

public record CreatePlanCommand(String name, String description, Double price) {

        public CreatePlanCommand {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be null or empty");
            }
            if (description == null || description.isBlank()) {
                throw new IllegalArgumentException("Description cannot be null or empty");
            }
            if (price == null) {
                throw new IllegalArgumentException("Price cannot be null");
            }
        }
}
