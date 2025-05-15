package com.techzo.cambiazo.donations.interfaces.rest;

import com.techzo.cambiazo.donations.domain.model.queries.GetAccountNumberByIdQuery;
import com.techzo.cambiazo.donations.domain.model.queries.GetAllAccountNumberByOngIdQuery;
import com.techzo.cambiazo.donations.domain.services.AccountNumberCommandService;
import com.techzo.cambiazo.donations.domain.services.AccountNumberQueryService;
import com.techzo.cambiazo.donations.interfaces.rest.resources.AccountNumberResource;
import com.techzo.cambiazo.donations.interfaces.rest.resources.CreateAccountNumberResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.techzo.cambiazo.donations.domain.model.aggregates.AccountNumber;
import com.techzo.cambiazo.donations.interfaces.rest.transform.AccountNumberResourceFromEntityAssembler;

class AccountNumberControllerTest {

    @Mock
    private AccountNumberCommandService accountNumberCommandService;

    @Mock
    private AccountNumberQueryService accountNumberQueryService;

    @InjectMocks
    private AccountNumberController accountNumberController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAccountNumberById() {
        Long id = 987654321L;
        AccountNumber accountNumber = new AccountNumber();
        // Configura manualmente las propiedades de AccountNumber si es necesario

        AccountNumberResource accountNumberResource = new AccountNumberResource(1L, "name", "cci", "account", 1L);

        when(accountNumberQueryService.handle(any(GetAccountNumberByIdQuery.class))).thenReturn(Optional.of(accountNumber));

        try (MockedStatic<AccountNumberResourceFromEntityAssembler> mockedStatic = mockStatic(AccountNumberResourceFromEntityAssembler.class)) {
            mockedStatic.when(() -> AccountNumberResourceFromEntityAssembler.toResourceFromEntity(any())).thenReturn(accountNumberResource);

            ResponseEntity<AccountNumberResource> response = accountNumberController.getAccountNumberById(id);

            assertEquals(200, response.getStatusCode().value());
            assertNotNull(response.getBody());
        }
    }

    @Test
    void testGetAllAccountNumberByOngId() {
        Long ongId = 987654321L;
        AccountNumber accountNumber = new AccountNumber();
        // Configura manualmente las propiedades de AccountNumber si es necesario

        List<AccountNumberResource> accountNumberResources = List.of(new AccountNumberResource(1L, "name", "cci", "account", 1L));

        when(accountNumberQueryService.handle(any(GetAllAccountNumberByOngIdQuery.class))).thenReturn(List.of(accountNumber));

        try (MockedStatic<AccountNumberResourceFromEntityAssembler> mockedStatic = mockStatic(AccountNumberResourceFromEntityAssembler.class)) {
            mockedStatic.when(() -> AccountNumberResourceFromEntityAssembler.toResourceFromEntity(any())).thenReturn(accountNumberResources.get(0));

            ResponseEntity<List<AccountNumberResource>> response = accountNumberController.getAllAccountNumberByOngId(ongId);

            assertEquals(200, response.getStatusCode().value());
            List<AccountNumberResource> body = response.getBody();
            assertNotNull(body);
            assertFalse(body.isEmpty());
        }
    }


}