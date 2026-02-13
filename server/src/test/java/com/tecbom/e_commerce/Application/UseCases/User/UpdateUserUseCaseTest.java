package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOUpdateUser;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.PhoneNumber;
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

class UpdateUserUseCaseTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UpdateUserUseCase updateUserUseCase;

    private DTOUpdateUser dtoUpdateUser;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        dtoUpdateUser = new ReferenceObject().dtoUpdateUser();
        user = new ReferenceObject().NormalUser();
    }


    @Test
    @DisplayName("Deve atualizar as informações do usuário com sucesso")
    void shouldUpdateUser() {
        when(repository.getUserByCpf(dtoUpdateUser.cpf())).thenReturn(Optional.of(user));
        when(mapper.addressByService(dtoUpdateUser.address())).thenReturn(new ReferenceObject().address2());
        User beforeUpdate = new ReferenceObject().NormalUser();
        updateUserUseCase.updateUser(dtoUpdateUser);
        verify(repository, times(1)).getUserByCpf(dtoUpdateUser.cpf());
        verify(mapper, times(1)).addressByService(dtoUpdateUser.address());
        verify(repository, times(1)).saveUser(user);
        assertNotEquals(beforeUpdate.getEmail(), user.getEmail());
        assertNotEquals(beforeUpdate.getPhoneNumber(), user.getPhoneNumber());
        assertNotEquals(beforeUpdate.getAddress(), user.getAddress());
    }
    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o usuário não for encontrado")
    void shouldThrowUserNotFoundException() {
        when(repository.getUserByCpf(dtoUpdateUser.cpf())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> updateUserUseCase.updateUser(dtoUpdateUser));
        verify(repository, times(1)).getUserByCpf(dtoUpdateUser.cpf());
        verify(mapper, times(0)).addressByService(any());
        verify(repository, times(0)).saveUser(any(User.class));
    }
    @Test
    @DisplayName("Deve atualizar apenas alguns atributos do usuário")
    void shouldUpdateOnlySomeAttributes() {
        DTOUpdateUser partialUpdate = new DTOUpdateUser(dtoUpdateUser.cpf(), null, new PhoneNumber("11999999999"), null);
        when(repository.getUserByCpf(partialUpdate.cpf())).thenReturn(Optional.of(user));
        User beforeUpdate = new ReferenceObject().NormalUser();
        updateUserUseCase.updateUser(partialUpdate);
        verify(repository, times(1)).getUserByCpf(partialUpdate.cpf());
        verify(repository, times(1)).saveUser(user);
        assertEquals(beforeUpdate.getEmail(), user.getEmail());
        assertNotEquals(beforeUpdate.getPhoneNumber(), user.getPhoneNumber());
        assertEquals(beforeUpdate.getAddress(), user.getAddress());
    }
}