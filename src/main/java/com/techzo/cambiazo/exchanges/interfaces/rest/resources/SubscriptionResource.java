package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SubscriptionResource(Long id, String State, Long planId, Long userId, LocalDateTime startDate, LocalDateTime endDate) {
}
