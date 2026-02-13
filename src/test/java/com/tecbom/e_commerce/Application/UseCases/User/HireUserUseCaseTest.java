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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HireUserUseCaseTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private HireUserUseCase hireUserUseCase;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new ReferenceObject().NormalUser();
    }

        @Test
        @DisplayName("Deve contratar um usuário com sucesso")
        void shouldHireUser() {
            assertEquals(user.getRole(), Role.COMUM);
            when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.of(user));
            hireUserUseCase.hireUser(user.getCpf());
            verify(repository, times(1)).getUserByCpf(user.getCpf());
            verify(repository, times(1)).saveUser(user);
            assertTrue(user.getRole().equals(Role.ADMIN));
            assertNotNull(user);
        }
        @Test
        @DisplayName("Deve lançar UserNotFoundException quando o usuário não for encontrado")
        void shouldThrowUserNotFoundException() {
            when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.empty());
            assertThrows(UserNotFoundException.class, () -> hireUserUseCase.hireUser(user.getCpf()));
            verify(repository, times(1)).getUserByCpf(user.getCpf());
            verify(repository, times(0)).saveUser(any(User.class));
            assertEquals(user.getRole(), Role.COMUM);
        }
        @Test
        @DisplayName("Deve lançar InvalidDataException quando o usuário já for um admin")
        void shouldNotHireUserAlreadyAdmin() {
            user.HireUser();
            when(repository.getUserByCpf(user.getCpf())).thenReturn(Optional.of(user));
            assertThrows(InvalidDataException.class, () -> hireUserUseCase.hireUser(user.getCpf()));
            verify(repository, times(1)).getUserByCpf(user.getCpf());
            verify(repository, times(0)).saveUser(any(User.class));
            assertEquals(user.getRole(), Role.ADMIN);
        }
}