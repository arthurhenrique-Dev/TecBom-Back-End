package com.tecbom.e_commerce.Application.DTOs.Users;

import com.tecbom.e_commerce.Domain.ValueObjects.*;

public record DTOReturnUser(

        Cpf cpf,
        Name name,
        EmailVO email,
        PhoneNumber phoneNumber,
        Address address
) {
}
