package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;

public record Cep(String cep) {

    public Cep(String cep) {
        if (cep.matches("^[0-9]{8}$")) this.cep = cep;
        else throw new InvalidDataException("CEP invalido");
    }
}
