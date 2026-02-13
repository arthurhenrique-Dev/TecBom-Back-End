package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUpdaterTest {

    @Test
    void deveIniciarComTokenEDataExpiracaoValida() {
        PasswordUpdater updater = PasswordUpdater.Start();

        assertNotNull(updater.token());
        assertTrue(updater.expirationDate().isAfter(LocalDateTime.now()));
    }

    @Test
    void deveRetornarTrueQuandoTokenEDataForemValidos() {
        PasswordUpdater updater = PasswordUpdater.Start();

        boolean result = updater.CheckToken(updater.token());

        assertTrue(result);
    }

    @Test
    void deveLancarExcecaoQuandoTokenForIncorreto() {
        PasswordUpdater updater = PasswordUpdater.Start();

        assertThrows(ValidationFailedException.class, () -> updater.CheckToken("token-invalido"));
    }

    @Test
    void deveLancarExcecaoQuandoTokenEstiverExpirado() {
        PasswordUpdater updater = new PasswordUpdater("token-123", LocalDateTime.now().minusMinutes(1));

        assertThrows(ValidationFailedException.class, () -> updater.CheckToken("token-123"));
    }
}