package com.techzo.cambiazo.exchanges.interfaces.rest.resources;

import java.time.LocalDate;
import java.util.Date;

public record ExchangeResource(Long id, Long productOwnId, Long productChangeId, String  status, LocalDate exchangeDate, Date createdAt, Date updatedAt) {
}
