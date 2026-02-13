package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidTextTest {

    @Test
    void deveCriarTextoValido() {
        String valor = "Algum texto de exemplo";
        ValidText validText = new ValidText(valor);
        assertEquals(valor, validText.text());
    }

    @Test
    void deveLancarExcecaoParaTextoVazioOuApenasEspacos() {
        assertThrows(InvalidDataException.class, () -> new ValidText(""));
        assertThrows(InvalidDataException.class, () -> new ValidText("   "));
    }

    @Test
    void deveLancarExcecaoParaTextoNulo() {
        assertThrows(InvalidDataException.class, () -> new ValidText(null));
    }
}