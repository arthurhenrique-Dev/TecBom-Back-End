package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOAddCartItem;
import com.tecbom.e_commerce.Application.Ports.Input.User.AddCartItemPort;
import com.tecbom.e_commerce.Application.Ports.Output.CartRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;

public class AddCartItemUseCase implements AddCartItemPort {

    private final CartRepository repository;

    public AddCartItemUseCase(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addCartItem(DTOAddCartItem dtoAddCartItem) {
        Cart cart = repository.getCart(dtoAddCartItem.cpf())
                .orElseThrow(() -> new UserNotFoundException());
        cart.addCartItem(dtoAddCartItem.cartItem());
        repository.saveCart(cart);
    }
}
