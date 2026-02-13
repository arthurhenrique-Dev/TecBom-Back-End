package com.tecbom.e_commerce.Application.DTOs.Users;

import com.tecbom.e_commerce.Domain.ValueObjects.CartItem;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public record DTOAddCartItem(

        Cpf cpf,
        CartItem cartItem
) {
}
