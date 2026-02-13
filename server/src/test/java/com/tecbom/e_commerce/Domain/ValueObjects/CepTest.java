package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CepTest {

    @Test
    void deveCriarCepValido() {
        String valor = "58101295";
        Cep cep = new Cep(valor);
        assertEquals(valor, cep.cep());
    }

    @Test
    void deveLancarExcecaoQuandoCepForInvalido() {
        assertThrows(InvalidDataException.class, () -> new Cep("123"));
        assertThrows(InvalidDataException.class, () -> new Cep("58101-295"));
        assertThrows(InvalidDataException.class, () -> new Cep("abc12345"));
    }
}