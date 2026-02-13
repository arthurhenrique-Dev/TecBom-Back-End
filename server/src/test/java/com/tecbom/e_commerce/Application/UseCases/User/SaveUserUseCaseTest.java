package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSaveUser;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Application.Ports.Output.CartRepository;
import com.tecbom.e_commerce.Application.Ports.Output.EmailService;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SaveUserUseCaseTest {

    @Mock
    private UserRepository repository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private SaveUserUseCase saveUserUseCase;

    private DTOSaveUser dtoSaveUser;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        dtoSaveUser = new ReferenceObject().dtoSaveUser();
        user = new ReferenceObject().UserRegister();
    }

    @Test
    @DisplayName("Deve salvar um usuário com sucesso")
    void sucessSaveUser() {
        when(repository.getUserByCpf(dtoSaveUser.cpf())).thenReturn(java.util.Optional.empty());
        when(userMapper.registerUser(dtoSaveUser)).thenReturn(user);
        saveUserUseCase.saveUser(dtoSaveUser);
        verify(repository, times(1)).getUserByCpf(dtoSaveUser.cpf());
        verify(userMapper, times(1)).registerUser(dtoSaveUser);
        verify(cartRepository, times(1)).saveCart(any());
        verify(repository, times(1)).saveUser(user);
        verify(emailService, times(1)).ValidateEmail(user.getEmail(), user.getEmailValidation().token());
    }

    @Test
    @DisplayName("Deve lançar ValidationFailedException quando o usuário já existir")
    void shouldThrowValidationFailedException() {
        when(repository.getUserByCpf(dtoSaveUser.cpf())).thenReturn(java.util.Optional.of(user));
        assertThrows(ValidationFailedException.class, () -> saveUserUseCase.saveUser(dtoSaveUser));
        verify(repository, times(1)).getUserByCpf(dtoSaveUser.cpf());
        verify(userMapper, times(0)).registerUser(any());
        verify(cartRepository, times(0)).saveCart(any());
        verify(repository, times(0)).saveUser(any());
        verify(emailService, times(0)).ValidateEmail(any(), any());
    }
}