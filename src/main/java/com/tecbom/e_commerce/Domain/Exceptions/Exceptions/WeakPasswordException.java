package com.tecbom.e_commerce.Domain.Exceptions.Exceptions;

public class WeakPasswordException extends RuntimeException {
    public WeakPasswordException() {
        super("Senha fraca, é necessário conter ao menos 8 caracteres, incluindo letras maiúsculas, minúsculas, números e símbolos especiais.");
    }
}
