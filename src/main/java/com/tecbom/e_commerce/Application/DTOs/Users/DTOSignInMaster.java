package com.tecbom.e_commerce.Application.DTOs.Users;

import com.tecbom.e_commerce.Domain.ValueObjects.*;

public record DTOSignInMaster(

        Cpf cpf,
        Name name,
        Password plainPassword,
        EmailVO email,
        PhoneNumber phoneNumber
) {
}
