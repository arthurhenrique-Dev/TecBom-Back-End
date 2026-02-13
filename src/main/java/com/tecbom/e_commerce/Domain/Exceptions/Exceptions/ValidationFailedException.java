package com.tecbom.e_commerce.Domain.Exceptions.Exceptions;

public class ValidationFailedException extends RuntimeException {
    public ValidationFailedException(String message) {
        super(message);
    }

    public ValidationFailedException() {
        super("validação falhou");
    }
}
