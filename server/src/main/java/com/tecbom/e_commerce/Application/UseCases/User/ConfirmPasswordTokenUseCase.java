package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOUpdatePasswordUser;
import com.tecbom.e_commerce.Application.Ports.Input.User.ConfirmPasswordTokenPort;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;

import java.time.LocalDateTime;

public class ConfirmPasswordTokenUseCase implements ConfirmPasswordTokenPort {

    private final UserRepository repository;

    public ConfirmPasswordTokenUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void confirmToken(DTOUpdatePasswordUser dtoUpdatePasswordUser) {
        User user = repository.getUserByCpf(dtoUpdatePasswordUser.cpf())
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getPasswordUpdater().expirationDate().isBefore(LocalDateTime.now())) {
            user.ClearPasswordUpdater();
            repository.saveUser(user);
            throw new ValidationFailedException("Token expirado");
        } else {
            user.ChangePassword(dtoUpdatePasswordUser.token(), dtoUpdatePasswordUser.password());
            repository.saveUser(user);
        }
    }
}
