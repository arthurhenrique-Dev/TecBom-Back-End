package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public interface GetCartPort {

    Cart getCart(Cpf cpf);
}
