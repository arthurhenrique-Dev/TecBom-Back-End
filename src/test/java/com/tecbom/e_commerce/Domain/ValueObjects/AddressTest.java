package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    @DisplayName("Deve criar um endereço válido com sucesso")
    void shouldCreateValidAddress() {
        Cep cep = new Cep("58101295");
        ValidText numero = new ValidText("123");

        Address address = new Address(
                cep,
                "Rua das Flores",
                "Centro",
                "João Pessoa",
                "PB",
                "Apto 101",
                numero
        );

        assertNotNull(address);
        assertEquals("58101295", address.cep().cep());
        assertEquals("123", address.numero().text());
    }

    @Test
    @DisplayName("Deve lançar InvalidDataException quando todos os campos obrigatórios forem nulos ou vazios")
    void shouldThrowExceptionWhenAllFieldsAreInvalid() {
        assertThrows(InvalidDataException.class, () -> new Address(
                null, null, null, null, null, null, null
        ));
    }

    @Test
    @DisplayName("Deve permitir criar endereço se pelo menos o CEP estiver preenchido")
    void shouldCreateAddressWithOnlyCep() {
        Cep cep = new Cep("58101295");

        Address address = new Address(
                cep, null, null, null, null, null, null
        );

        assertNotNull(address);
        assertEquals("58101295", address.cep().cep());
    }

    @Test
    @DisplayName("Deve lançar InvalidDataException quando strings forem vazias")
    void shouldThrowExceptionWhenFieldsAreEmptyStrings() {
        assertThrows(InvalidDataException.class, () -> new Address(
                new Cep(""), "", "", "", "", "", new ValidText("")
        ));
    }
}