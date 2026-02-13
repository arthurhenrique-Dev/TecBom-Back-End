package com.tecbom.e_commerce.Application.Ports.Output;

import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

import java.util.Optional;

public interface CartRepository {

    void saveCart(Cart cart);
    Optional<Cart> getCart(Cpf cpf);
}
