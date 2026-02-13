package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailVOTest {

    @Test
    void deveCriarEmailValido() {
        String valor = "contato@tecbom.com.br";
        EmailVO email = new EmailVO(valor);
        assertEquals(valor, email.email());
    }

    @Test
    void deveRejeitarEmailsInvalidos() {
        assertThrows(InvalidDataException.class, () -> new EmailVO("email_invalido"));
        assertThrows(InvalidDataException.class, () -> new EmailVO("@dominio.com"));
        assertThrows(InvalidDataException.class, () -> new EmailVO("arthur@.com"));
        assertThrows(InvalidDataException.class, () -> new EmailVO(null));
    }
}