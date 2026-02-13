package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NaturalTest {

    @Test
    void deveCriarNaturalComSucesso() {
        Natural n = new Natural(10);
        assertEquals(10, n.i());
    }

    @Test
    void deveAceitarZero() {
        Natural n = new Natural(0);
        assertEquals(0, n.i());
    }

    @Test
    void deveLancarExcecaoParaNegativo() {
        assertThrows(InvalidDataException.class, () -> new Natural(-1));
    }

    @Test
    void deveLancarExcecaoParaNulo() {
        // O Java estoura NullPointerException ao tentar comparar i >= 0 se for null
        assertThrows(NullPointerException.class, () -> new Natural(null));
    }
}