package com.tecbom.e_commerce.Domain.Exceptions.Exceptions;

public class ProcessingErrorException extends RuntimeException {
    public ProcessingErrorException() {
        super("Erro de processamento");
    }
}
