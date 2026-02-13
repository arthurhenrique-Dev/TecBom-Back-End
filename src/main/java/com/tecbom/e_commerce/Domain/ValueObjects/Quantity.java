package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;

public record Quantity(Integer quantity) {

    public Quantity(Integer quantity) {
        if (quantity >= 0) this.quantity = quantity;
        else throw new InvalidDataException();
    }
}
