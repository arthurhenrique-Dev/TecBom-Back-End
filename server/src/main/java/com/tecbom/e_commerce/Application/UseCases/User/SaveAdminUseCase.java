package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSaveUser;
import com.tecbom.e_commerce.Application.Ports.Input.User.SaveAdminPort;
import com.tecbom.e_commerce.Application.Ports.Output.CartRepository;
import com.tecbom.e_commerce.Application.Ports.Output.EmailService;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.Entities.Users.Role;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;

import java.util.Optional;

public class SaveAdminUseCase implements SaveAdminPort {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final EmailService service;
    private final CartRepository cartRepository;

    public SaveAdminUseCase(UserRepository repository, UserMapper mapper, EmailService service, CartRepository cartRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.service = service;
        this.cartRepository = cartRepository;
    }

    @Override
    public void saveAdmin(DTOSaveUser dtoSaveUser) {
        Optional<User> existingUser = repository.getUserByCpf(dtoSaveUser.cpf());
        if (existingUser.isPresent()) {
            if (existingUser.get().getRole() == Role.COMUM) {
                existingUser.get().HireUser();
                repository.saveUser(existingUser.get());
            } else {
                throw new InvalidDataException("O usuário já é um admin.");
            }
        } else {
            User readyToSave = mapper.registerAdmin(dtoSaveUser);
            repository.saveUser(readyToSave);
            service.ValidateEmail(readyToSave.getEmail(), readyToSave.getEmailValidation().token());
            cartRepository.saveCart(new Cart(readyToSave.getCpf()));
        }
    }
}
