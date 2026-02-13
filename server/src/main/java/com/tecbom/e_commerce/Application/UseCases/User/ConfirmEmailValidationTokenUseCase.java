package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOEmailValidation;
import com.tecbom.e_commerce.Application.Ports.Input.User.ConfirmEmailValidationTokenPort;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;

public class ConfirmEmailValidationTokenUseCase implements ConfirmEmailValidationTokenPort {

    private final UserRepository repository;

    public ConfirmEmailValidationTokenUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void confirmEmailValidationToken(DTOEmailValidation dtoEmailValidation) {
        User existingUser = repository.getUserByCpf(dtoEmailValidation.cpf())
                .orElseThrow(() -> new UserNotFoundException());
        existingUser.ValidateEmail(dtoEmailValidation.token());
        repository.saveUser(existingUser);
    }
}
