package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

import java.time.LocalDate;

public record SubscriptionResource(Long id, String State, Long planId, Long userId, LocalDate startDate, LocalDate endDate) {
}
