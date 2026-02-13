package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;

import java.time.LocalDateTime;

public record PasswordUpdater(

        String token,
        LocalDateTime expirationDate
) {

    public static PasswordUpdater Start() {
        String token = java.util.UUID.randomUUID().toString();
        LocalDateTime expirationDate = LocalDateTime.now().plusHours(1);
        return new PasswordUpdater(token, expirationDate);
    }

    public boolean CheckToken(String token) {
        if (token.equals(this.token) && LocalDateTime.now().isBefore(this.expirationDate)) {
            return true;
        } else throw new ValidationFailedException();
    }
}
