package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidationTest {

    @Test
    void deveIniciarValidacaoComTokenEStatusFalso() {
        EmailValidation auth = EmailValidation.Start();

        assertNotNull(auth.token());
        assertFalse(auth.validated());
    }

    @Test
    void deveValidarComSucessoQuandoTokenForIgual() {
        EmailValidation inicial = EmailValidation.Start();
        String tokenGerado = inicial.token();

        EmailValidation resultado = inicial.Validate(tokenGerado);

        assertTrue(resultado.validated());
        assertNull(resultado.token());
    }

    @Test
    void deveLancarExcecaoQuandoTokenForDiferente() {
        EmailValidation auth = EmailValidation.Start();

        assertThrows(ValidationFailedException.class, () -> auth.Validate("token-errado"));
    }

    @Test
    void deveManterEstadoSeJaEstiverValidado() {
        EmailValidation jaValidado = new EmailValidation(null, true);

        EmailValidation resultado = jaValidado.Validate("qualquer-coisa");

        assertTrue(resultado.validated());
        assertSame(jaValidado, resultado);
    }
}