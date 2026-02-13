package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Input.User.GetCartPort;
import com.tecbom.e_commerce.Application.Ports.Output.CartRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public class GetCartUseCase implements GetCartPort {

    private final CartRepository repository;

    public GetCartUseCase(CartRepository repository) {
        this.repository = repository;
    }

    @Override
    public Cart getCart(Cpf cpf) {
        return repository.getCart(cpf)
                .orElseThrow(() -> new UserNotFoundException());
    }
}
