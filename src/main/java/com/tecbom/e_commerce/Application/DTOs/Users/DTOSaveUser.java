package com.tecbom.e_commerce.Application.DTOs.Users;

import com.tecbom.e_commerce.Domain.ValueObjects.*;

public record DTOSaveUser(

        Cpf cpf,
        Name name,
        Password password,
        EmailVO email,
        DTOSaveAddress address,
        PhoneNumber phoneNumber
) {
}
