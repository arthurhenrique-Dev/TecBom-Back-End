package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void deveCriarNomeValido() {
        String valor = "Arthur Conceição";
        Name name = new Name(valor);
        assertEquals(valor, name.name());
    }

    @Test
    void deveRejeitarNomesInvalidos() {
        assertThrows(InvalidDataException.class, () -> new Name("A"));
        assertThrows(InvalidDataException.class, () -> new Name("Arthur123"));
        assertThrows(InvalidDataException.class, () -> new Name("Art@ur"));
        assertThrows(InvalidDataException.class, () -> new Name(null));
        assertThrows(InvalidDataException.class, () -> new Name("  "));
    }
}