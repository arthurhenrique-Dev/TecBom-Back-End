package com.tecbom.e_commerce.Application.DTOs.Users;

import com.tecbom.e_commerce.Domain.ValueObjects.Cep;
import com.tecbom.e_commerce.Domain.ValueObjects.ValidText;

public record DTOSaveAddress(

        Cep cep,
        ValidText numero,
        String complemento
) {
}
