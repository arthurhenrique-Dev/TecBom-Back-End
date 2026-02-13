package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;

public record ValidText(String text) {

    public ValidText(String text) {
        if (text != null && !text.trim().isEmpty()) this.text = text;
        else throw new InvalidDataException("Texto inválido");
    }
}
