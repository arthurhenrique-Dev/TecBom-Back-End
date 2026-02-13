package com.tecbom.e_commerce.Application.DTOs.Users;

import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;
import com.tecbom.e_commerce.Domain.ValueObjects.Password;

public record DTOUpdatePasswordUser(

        Cpf cpf,
        String token,
        Password password
) {
}
