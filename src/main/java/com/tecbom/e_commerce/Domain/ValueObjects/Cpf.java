package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;

public record Cpf(String cpf) {

    private boolean Validar(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            return false;
        }

        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        int[] digitos = cpf.chars().map(c -> c - '0').toArray();

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += digitos[i] * (10 - i);
        }
        int primeiroDigito = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += digitos[i] * (11 - i);
        }
        int segundoDigito = sum % 11 < 2 ? 0 : 11 - (sum % 11);

        return digitos[9] == primeiroDigito && digitos[10] == segundoDigito;
    }

    public Cpf(String cpf) {
        if (this.Validar(cpf)) this.cpf = cpf;
        else throw new InvalidDataException("CPF invalido");
    }
}
