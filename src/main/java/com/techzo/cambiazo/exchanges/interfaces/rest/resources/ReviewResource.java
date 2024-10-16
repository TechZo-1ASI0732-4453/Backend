package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

public record ReviewResource(Long id, String message, Integer rating, String state, Long exchangeId, Long userAuthorId, Long userReceptorId) {
}
