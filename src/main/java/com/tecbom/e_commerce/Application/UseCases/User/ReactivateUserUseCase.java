package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Input.User.ReactivateUserPort;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Entities.Users.Status;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public class ReactivateUserUseCase implements ReactivateUserPort {

    private final UserRepository repository;

    public ReactivateUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void reactivateUser(Cpf cpf) {
        User user = repository.getUserByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getStatus() == Status.OFF) {
            user.Reactivate();
            repository.saveUser(user);
        } else throw new InvalidDataException("Usuario não necessita de reativação");
    }
}
