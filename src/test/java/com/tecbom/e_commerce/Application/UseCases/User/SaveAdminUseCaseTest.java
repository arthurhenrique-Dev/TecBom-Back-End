package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSaveUser;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Application.Ports.Output.CartRepository;
import com.tecbom.e_commerce.Application.Ports.Output.EmailService;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Role;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
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

class SaveAdminUseCaseTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private EmailService emailService;

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private SaveAdminUseCase saveAdminUseCase;

    private DTOSaveUser dtoSaveUser;

    private User user;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        dtoSaveUser = new ReferenceObject().dtoSaveUser();
        user = new ReferenceObject().NormalUser();
    }

    @Test
    @DisplayName("Deve salvar um usuário admin com sucesso")
    void sucessSaveAdmin() {
        when(repository.getUserByCpf(dtoSaveUser.cpf())).thenReturn(java.util.Optional.empty());
        when(userMapper.registerAdmin(dtoSaveUser)).thenReturn(user);
        saveAdminUseCase.saveAdmin(dtoSaveUser);
        verify(repository, times(1)).getUserByCpf(dtoSaveUser.cpf());
        verify(userMapper, times(1)).registerAdmin(dtoSaveUser);
        verify(repository, times(1)).saveUser(user);
        verify(emailService, times(1)).ValidateEmail(user.getEmail(), user.getEmailValidation().token());
        verify(cartRepository, times(1)).saveCart(any());
    }

    @Test
    @DisplayName("Deve contratar um usuário existente como admin")
    void shouldHireExistingUserAsAdmin() {
        when(repository.getUserByCpf(dtoSaveUser.cpf())).thenReturn(java.util.Optional.of(user));
        assertEquals(user.getRole(), Role.COMUM);
        saveAdminUseCase.saveAdmin(dtoSaveUser);
        verify(repository, times(1)).getUserByCpf(dtoSaveUser.cpf());
        verify(repository, times(1)).saveUser(user);
        assertEquals(user.getRole(), user.getRole().ADMIN);
    }

    @Test
    @DisplayName("Deve lançar InvalidDataException quando o usuário já for um admin")
    void shouldThrowInvalidDataExceptionWhenUserIsAlreadyAdmin() {
        user.HireUser();
        when(repository.getUserByCpf(dtoSaveUser.cpf())).thenReturn(java.util.Optional.of(user));
        assertThrows(InvalidDataException.class, () -> saveAdminUseCase.saveAdmin(dtoSaveUser));
        verify(repository, times(1)).getUserByCpf(dtoSaveUser.cpf());
        verify(repository, times(0)).saveUser(any(User.class));
    }
}