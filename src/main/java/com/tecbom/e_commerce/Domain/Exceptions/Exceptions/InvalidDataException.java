package com.tecbom.e_commerce.Domain.Exceptions.Exceptions;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException() {
        super("dados inválidos inseridos");
    }
}
