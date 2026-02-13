package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Status;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
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

class ReactivateUserUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ReactivateUserUseCase reactivateUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new ReferenceObject().NormalUser();
    }

    @Test
    @DisplayName("Deve reativar um usuário com sucesso")
    void sucessRactivateUser() {
        user.Deactivate();
        assertEquals(user.getStatus(), Status.OFF);
        when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.of(user));
        reactivateUserUseCase.reactivateUser(user.getCpf());
        verify(repository, times(1)).getUserByCpf(user.getCpf());
        verify(repository, times(1)).saveUser(user);
        assertEquals(user.getStatus(), Status.ON);
    }

    @Test
    @DisplayName("Deve lançar InvalidDataException quando o usuário já estiver ativo")
    void userAlreadyActive() {
        assertEquals(user.getStatus(), Status.ON);
        when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.of(user));
        assertThrows(InvalidDataException.class, () -> reactivateUserUseCase.reactivateUser(user.getCpf()));
        verify(repository, times(1)).getUserByCpf(user.getCpf());
        verify(repository, times(0)).saveUser(user);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o usuário não for encontrado")
    void shouldThrowUserNotFoundException() {
        when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.empty());
        assertThrows(com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException.class, () -> reactivateUserUseCase.reactivateUser(user.getCpf()));
        verify(repository, times(1)).getUserByCpf(user.getCpf());
        verify(repository, times(0)).saveUser(any(User.class));
    }
}