package com.tecbom.e_commerce.Application.DTOs.Users;

import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;
import com.tecbom.e_commerce.Domain.ValueObjects.EmailVO;
import com.tecbom.e_commerce.Domain.ValueObjects.Name;
import com.tecbom.e_commerce.Domain.ValueObjects.PhoneNumber;

public record DTOSearchUser(

        Cpf cpf,
        Name name,
        EmailVO email,
        PhoneNumber phoneNumber
) {
}
