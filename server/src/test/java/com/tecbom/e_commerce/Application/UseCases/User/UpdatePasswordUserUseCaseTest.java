package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Output.EmailService;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.EmailVO;
import com.tecbom.e_commerce.Domain.ValueObjects.Password;
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

class UpdatePasswordUserUseCaseTest {

    @Mock
    private UserRepository repository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UpdatePasswordUserUseCase updatePasswordUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new ReferenceObject().NormalUser();
    }

    @Test
    @DisplayName("Deve enviar a solicitação de alteração na senha do usuário com sucesso")
    void sucessUpdatePassword() {
        when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.of(user));
        updatePasswordUserUseCase.updatePassword(user.getCpf());
        assertNotNull(user.getPasswordUpdater());
        verify(emailService, times(1)).ChangePassword(user.getEmail(), user.getPasswordUpdater().token());
    }
    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o usuário não for encontrado")
    void shouldThrowUserNotFoundException() {
        when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> updatePasswordUserUseCase.updatePassword(user.getCpf()));
        verify(repository, times(1)).getUserByCpf(user.getCpf());
        verify(emailService, times(0)).ValidateEmail(any(EmailVO.class), anyString());
    }
}