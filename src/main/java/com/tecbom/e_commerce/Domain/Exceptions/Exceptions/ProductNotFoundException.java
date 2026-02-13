package com.tecbom.e_commerce.Domain.Exceptions.Exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException() {
        super("Produto não encontrado");
    }
}
