package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOEmailValidation;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfirmEmailValidationTokenUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ConfirmEmailValidationTokenUseCase confirmEmailValidationTokenUseCase;

    private DTOEmailValidation dtoEmailValidation;

    private User user;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        dtoEmailValidation = new ReferenceObject().dtoEmailValidation();
        user = new ReferenceObject().NormalUserInvalid();
    }

    @Test
    @DisplayName("Deve confirmar o token de validação de email com sucesso")
    void sucessConfirmEmailValidationToken() {
        when(repository.getUserByCpf(dtoEmailValidation.cpf())).thenReturn(Optional.of(user));
        confirmEmailValidationTokenUseCase.confirmEmailValidationToken(dtoEmailValidation);
        verify(repository, times(1)).saveUser(user);
    }
    @Test
    @DisplayName("Deve lançar uma exceção ao tentar confirmar um token de validação de email inválido")
    void failConfirmEmailValidationToken() {
        when(repository.getUserByCpf(dtoEmailValidation.cpf())).thenReturn(Optional.of(user));
        DTOEmailValidation invalidDTO = new DTOEmailValidation(dtoEmailValidation.cpf(), "cara esse token ta tipo muito inválido tipo mesmo tipo não tem nada a ver com o token real namoralzinha mesmo");
        assertThrows(ValidationFailedException.class, () -> confirmEmailValidationTokenUseCase.confirmEmailValidationToken(invalidDTO));
        verify(repository, times(0)).saveUser(user);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar confirmar um token de validação de email para um usuário inexistente")
    void failConfirmEmailValidationTokenUserNotFound() {
        when(repository.getUserByCpf(dtoEmailValidation.cpf())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> confirmEmailValidationTokenUseCase.confirmEmailValidationToken(dtoEmailValidation));
        verify(repository, times(0)).saveUser(any(User.class));
    }
}