package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CpfTest {

    @Test
    void deveCriarCpfValido() {
        String valorValido = "10917087038";
        Cpf cpf = new Cpf(valorValido);
        assertEquals(valorValido, cpf.cpf());
    }

    @Test
    void deveRejeitarCpfsInvalidos() {
        assertThrows(InvalidDataException.class, () -> new Cpf("11111111111"));
        assertThrows(InvalidDataException.class, () -> new Cpf("10917087000"));
        assertThrows(InvalidDataException.class, () -> new Cpf("12345"));
        assertThrows(InvalidDataException.class, () -> new Cpf(null));
    }
}