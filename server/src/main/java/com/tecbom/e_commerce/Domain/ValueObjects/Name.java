package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;

public record Name(String name) {

    public Name(String name) {
        if (name != null && name.matches("^[\\p{L}][\\p{L} ]{1,249}$")) this.name = name;
        else throw new InvalidDataException("Nome invalido");
    }
}
