package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;

public record EmailVO(String email) {

    public EmailVO(String email) {
        if (email != null && email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")) {
            this.email = email;
        } else throw new InvalidDataException("Email invalido");
    }
}
