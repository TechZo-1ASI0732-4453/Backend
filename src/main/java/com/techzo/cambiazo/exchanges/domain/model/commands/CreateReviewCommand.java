package com.techzo.cambiazo.exchanges.domain.model.commands;

public record CreateReviewCommand(String message, Integer rating, String state, Long exchangeId, Long userAuthorId, Long userReceptorId) {

        public CreateReviewCommand {
            if (message == null || message.isBlank()) {
                throw new IllegalArgumentException("Message is required");
            }
            if (rating == null) {
                throw new IllegalArgumentException("Rating is required");
            }
            if (state == null || state.isBlank()) {
                throw new IllegalArgumentException("State is required");
            }
            if (exchangeId == null) {
                throw new IllegalArgumentException("ExchangeId is required");
            }
            if (userAuthorId == null) {
                throw new IllegalArgumentException("UserAuthorId is required");
            }
            if (userReceptorId == null) {
                throw new IllegalArgumentException("UserReceptorId is required");
            }
        }
}
