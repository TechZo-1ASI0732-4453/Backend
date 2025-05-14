package com.techzo.cambiazo.donations.application.internal.commandservices;

import com.techzo.cambiazo.donations.domain.exceptions.OngNotFoundException;
import com.techzo.cambiazo.donations.domain.model.aggregates.AccountNumber;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.commands.CreateAccountNumberCommand;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.AccountNumberRepository;
import com.techzo.cambiazo.donations.infrastructure.persistence.jpa.OngRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountNumberCommandServiceImplTest {

    private final AccountNumberRepository accountNumberRepository = mock(AccountNumberRepository.class);
    private final OngRepository ongRepository = mock(OngRepository.class);
    private final AccountNumberCommandServiceImpl service = new AccountNumberCommandServiceImpl(accountNumberRepository, ongRepository);

    @Test
    void testShouldCreateAccountNumber_WhenValidCommand() {
        // Arrange
        var command = new CreateAccountNumberCommand("987654321", "Test Name", "123456789", Long.valueOf("987654321"));
        var ong = mock(Ong.class);
        when(ongRepository.findById(Long.valueOf("987654321"))).thenReturn(Optional.of(ong));
        when(accountNumberRepository.findByNameAndCciAndAccount(any(), any(), any())).thenReturn(Optional.empty());

        // Act
        Optional<AccountNumber> result = service.handle(command);

        // Assert
        assertTrue(result.isPresent());
        verify(accountNumberRepository, times(1)).save(any(AccountNumber.class));
    }

    @Test
    void testShouldThrowOngNotFoundException_WhenOngDoesNotExist() {
        // Arrange
        var command = new CreateAccountNumberCommand("1", "Test Name", "123456789", Long.valueOf("987654321"));
        when(ongRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(OngNotFoundException.class, () -> service.handle(command));
    }
}
