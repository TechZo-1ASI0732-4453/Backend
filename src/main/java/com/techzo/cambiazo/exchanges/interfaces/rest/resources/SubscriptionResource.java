package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SubscriptionResource(Long id, String state, Long planId, Long userId, LocalDateTime startDate, LocalDateTime endDate) {
}
