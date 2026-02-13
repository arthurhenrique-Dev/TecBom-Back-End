package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {

    @Test
    void deveCriarQuantidadeValida() {
        Quantity q = new Quantity(10);
        assertEquals(10, q.quantity());
    }

    @Test
    void deveAceitarQuantidadeZero() {
        Quantity q = new Quantity(0);
        assertEquals(0, q.quantity());
    }

    @Test
    void deveLancarExcecaoParaQuantidadeNegativa() {
        assertThrows(InvalidDataException.class, () -> new Quantity(-1));
    }

    @Test
    void deveLancarNullPointerExceptionParaNulo() {
        assertThrows(NullPointerException.class, () -> new Quantity(null));
    }
}