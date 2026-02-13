package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.WeakPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void deveCriarSenhaForteComSucesso() {
        String senhaValida = "TecBom@2026";
        Password password = new Password(senhaValida);
        assertEquals(senhaValida, password.password());
    }

    @Test
    void deveRejeitarSenhasFracas() {
        assertThrows(WeakPasswordException.class, () -> new Password("12345678"));
        assertThrows(WeakPasswordException.class, () -> new Password("abcdefgh"));
        assertThrows(WeakPasswordException.class, () -> new Password("ABCDEFGH"));
        assertThrows(WeakPasswordException.class, () -> new Password("Ab1@"));
        assertThrows(WeakPasswordException.class, () -> new Password("Abcdefgh1"));
    }

    @Test
    void deveLancarNullPointerExceptionParaNulo() {
        assertThrows(NullPointerException.class, () -> new Password(null));
    }
}