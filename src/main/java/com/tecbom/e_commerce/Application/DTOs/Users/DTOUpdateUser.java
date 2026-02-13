package com.tecbom.e_commerce.Application.DTOs.Users;

import com.tecbom.e_commerce.Domain.ValueObjects.Address;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;
import com.tecbom.e_commerce.Domain.ValueObjects.EmailVO;
import com.tecbom.e_commerce.Domain.ValueObjects.PhoneNumber;

public record DTOUpdateUser(

        Cpf cpf,
        EmailVO email,
        PhoneNumber phoneNumber,
        DTOSaveAddress address
) {
}
