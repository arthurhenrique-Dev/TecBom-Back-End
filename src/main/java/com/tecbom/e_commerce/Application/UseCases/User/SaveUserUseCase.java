package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSaveUser;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Application.Ports.Input.User.SaveUserPort;
import com.tecbom.e_commerce.Application.Ports.Output.CartRepository;
import com.tecbom.e_commerce.Application.Ports.Output.EmailService;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;

import java.util.Optional;

public class SaveUserUseCase implements SaveUserPort {

    private final UserRepository repository;
    private final CartRepository cartRepository;
    private final UserMapper mapper;
    private final EmailService service;

    public SaveUserUseCase(UserRepository repository, CartRepository cartRepository, UserMapper mapper, EmailService service) {
        this.repository = repository;
        this.cartRepository = cartRepository;
        this.mapper = mapper;
        this.service = service;
    }

    @Override
    public void saveUser(DTOSaveUser dtoSaveUser) {
        Optional<User> existingUser = repository.getUserByCpf(dtoSaveUser.cpf());
        if (existingUser.isPresent()) throw new ValidationFailedException("Usuario existente");
        User readyToSave = mapper.registerUser(dtoSaveUser);
        cartRepository.saveCart(new Cart(dtoSaveUser.cpf()));
        repository.saveUser(readyToSave);
        service.ValidateEmail(readyToSave.getEmail(), readyToSave.getEmailValidation().token());
    }
}
