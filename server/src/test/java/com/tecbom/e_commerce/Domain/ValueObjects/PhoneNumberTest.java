package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void deveCriarNumeroValido() {
        String valor = "83988887777";
        PhoneNumber phoneNumber = new PhoneNumber(valor);
        assertEquals(valor, phoneNumber.number());
    }

    @Test
    void deveRejeitarNumerosInvalidos() {
        assertThrows(InvalidDataException.class, () -> new PhoneNumber("8388887777"));  // Sem o 9
        assertThrows(InvalidDataException.class, () -> new PhoneNumber("00988887777")); // DDD com zero
        assertThrows(InvalidDataException.class, () -> new PhoneNumber("8398888777"));  // Curto
        assertThrows(InvalidDataException.class, () -> new PhoneNumber("839888877777")); // Longo
        assertThrows(InvalidDataException.class, () -> new PhoneNumber(null));
    }
}