package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Role;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class DissmissAdminUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private DissmissAdminUseCase dissmissAdminUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new ReferenceObject().NormalUser();
    }
    @Test
    @DisplayName("Deve demitir um usuário admin com sucesso")
    void sucessDissmissAdmin() {
        assertEquals(user.getRole(), Role.COMUM);
        user.HireUser();
        assertEquals(user.getRole(), Role.ADMIN);
        when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.of(user));
        dissmissAdminUseCase.dissmissAdmin(user.getCpf());
        verify(repository, times(1)).getUserByCpf(user.getCpf());
        verify(repository, times(1)).saveUser(user);
        assertEquals(user.getRole(), Role.COMUM);
    }
    @Test
    @DisplayName("Deve lançar InvalidDataException quando o usuário não for admin")
    void shouldThrowInvalidDataException() {
        when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.of(user));
        assertThrows(InvalidDataException.class, () -> dissmissAdminUseCase.dissmissAdmin(user.getCpf()));
        verify(repository, times(1)).getUserByCpf(user.getCpf());
        verify(repository, times(0)).saveUser(any(User.class));
        assertEquals(user.getRole(), Role.COMUM);
    }
    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o usuário não for encontrado")
    void shouldThrowUserNotFoundException() {
        when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> dissmissAdminUseCase.dissmissAdmin(user.getCpf()));
        verify(repository, times(1)).getUserByCpf(user.getCpf());
        verify(repository, times(0)).saveUser(any(User.class));
    }
}