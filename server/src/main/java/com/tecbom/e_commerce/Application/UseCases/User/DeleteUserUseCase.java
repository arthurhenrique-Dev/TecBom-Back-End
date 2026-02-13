package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Input.User.DeleteUserPort;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Entities.Users.Status;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;


public class DeleteUserUseCase implements DeleteUserPort {

    private final UserRepository repository;

    public DeleteUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteUserByCpf(Cpf cpf) {
        User existingUser = repository.getUserByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        if (existingUser.getStatus() == Status.ON) {
            existingUser.Deactivate();
            repository.saveUser(existingUser);
        } else throw new InvalidDataException("Usuário já inativo");
    }
}
