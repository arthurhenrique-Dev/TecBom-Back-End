package com.tecbom.e_commerce.Application.DTOs.Users;

import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public record DTOEmailValidation(

        Cpf cpf,
        String token
) {
}
