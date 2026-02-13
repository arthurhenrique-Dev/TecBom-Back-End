package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
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

class DeleteUserUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private DeleteUserUseCase deleteUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new ReferenceObject().NormalUser();
    }
    @Test
    @DisplayName("Deve deletar um usuário com sucesso")
    void sucessDeleteUserByCpf() {
        when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.of(user));
        deleteUserUseCase.deleteUserByCpf(user.getCpf());
        verify(repository, times(1)).getUserByCpf(user.getCpf());
        verify(repository, times(1)).saveUser(user);
        assertEquals(user.getStatus(), user.getStatus().OFF);
    }
     @Test
     @DisplayName("Deve lançar InvalidDataException quando o usuário já estiver inativo")
     void userAlreadyDeleted() {
         user.Deactivate();
         when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.of(user));
         assertThrows(InvalidDataException.class, () -> deleteUserUseCase.deleteUserByCpf(user.getCpf()));
         verify(repository, times(1)).getUserByCpf(user.getCpf());
         verify(repository, times(0)).saveUser(user);
         assertEquals(user.getStatus(), user.getStatus().OFF);
    }
    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o usuário não for encontrado")
    void shouldThrowUserNotFoundException() {
        when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.empty());
        assertThrows(com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException.class, () -> deleteUserUseCase.deleteUserByCpf(user.getCpf()));
        verify(repository, times(1)).getUserByCpf(user.getCpf());
        verify(repository, times(0)).saveUser(any(User.class));
    }
}