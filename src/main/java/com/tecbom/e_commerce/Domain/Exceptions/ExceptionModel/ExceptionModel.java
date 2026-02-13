package com.tecbom.e_commerce.Domain.Exceptions.ExceptionModel;

import java.time.Instant;

public class ExceptionModel {

    private String message;
    private Instant timestamp;

    public ExceptionModel(String message) {
        this.message = message;
        this.timestamp = Instant.now();
    }
}
