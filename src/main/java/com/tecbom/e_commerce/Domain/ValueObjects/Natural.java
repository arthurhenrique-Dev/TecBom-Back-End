package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;

public record Natural(Integer i) {

    public Natural(Integer i) {
        if (i >= 0) this.i = i;
        else throw new InvalidDataException();
    }
}
