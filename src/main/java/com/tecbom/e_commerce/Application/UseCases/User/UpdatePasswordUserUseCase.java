package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Input.User.UpdatePasswordUserPort;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Application.Ports.Output.EmailService;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public class UpdatePasswordUserUseCase implements UpdatePasswordUserPort {

    private final UserRepository repository;
    private final EmailService service;

    public UpdatePasswordUserUseCase(UserRepository repository, EmailService service) {
        this.repository = repository;
        this.service = service;
    }


    @Override
    public void updatePassword(Cpf cpf) {
        User existingUser = repository.getUserByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        existingUser.StartChangePassword();
        repository.saveUser(existingUser);
        service.ChangePassword(existingUser.getEmail(), existingUser.getPasswordUpdater().token());
    }
}
