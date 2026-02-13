package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOUpdatePasswordUser;
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

class ConfirmPasswordTokenUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ConfirmPasswordTokenUseCase confirmPasswordTokenUseCase;

    private User user, userTokenExpired;

    private DTOUpdatePasswordUser dtoUpdatePasswordUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new ReferenceObject().NormalUserUpdatingPassword();
        userTokenExpired = new ReferenceObject().NormalUserUpdatingPasswordTokenExpired();
        dtoUpdatePasswordUser = new ReferenceObject().dtoUpdatePasswordUser();
    }

    @Test
    @DisplayName("Deve confirmar o token de atualização de senha com sucesso")
    void sucessConfirmToken() {
        when(repository.getUserByCpf(dtoUpdatePasswordUser.cpf())).thenReturn(Optional.of(user));
        confirmPasswordTokenUseCase.confirmToken(dtoUpdatePasswordUser);
        verify(repository, times(1)).getUserByCpf(dtoUpdatePasswordUser.cpf());
        verify(repository, times(1)).saveUser(user);
        assertNull(user.getPasswordUpdater());
        assertEquals(dtoUpdatePasswordUser.password(), user.getPassword());
    }
    @Test
    @DisplayName("Deve lançar uma exceção ao tentar confirmar um token de atualização de senha expirado")
    void failConfirmTokenExpired() {
        when(repository.getUserByCpf(dtoUpdatePasswordUser.cpf())).thenReturn(Optional.of(userTokenExpired));
        assertThrows(ValidationFailedException.class, () -> confirmPasswordTokenUseCase.confirmToken(dtoUpdatePasswordUser));
        verify(repository, times(1)).saveUser(userTokenExpired);
        verify(repository, times(1)).getUserByCpf(dtoUpdatePasswordUser.cpf());
        assertNull(userTokenExpired.getPasswordUpdater());
    }
    @Test
    @DisplayName("Deve lançar uma exceção ao tentar confirmar um token de atualização de senha para um usuário inexistente")
    void failConfirmTokenUserNotFound() {
        when(repository.getUserByCpf(dtoUpdatePasswordUser.cpf())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> confirmPasswordTokenUseCase.confirmToken(dtoUpdatePasswordUser));
        verify(repository, times(1)).getUserByCpf(dtoUpdatePasswordUser.cpf());
        verify(repository, times(0)).saveUser(any(User.class));
    }
}