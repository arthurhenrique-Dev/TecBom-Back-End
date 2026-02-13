package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOReturnUser;
import com.tecbom.e_commerce.Application.DTOs.Users.DTOSearchUser;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.ValueObjects.*;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchUserUseCaseTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private SearchUserUseCase searchUserUseCase;

    private User user;
    private DTOReturnUser dtoReturnUser;
    private DTOSearchUser dtoSearchUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new ReferenceObject().NormalUser();

        dtoReturnUser = new DTOReturnUser(
                user.getCpf(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress()
        );

        dtoSearchUser = new DTOSearchUser(
                user.getCpf(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber()
        );
    }

    @Test
    @DisplayName("Deve buscar usuários com filtros e paginação")
    void shouldSearchUsersWithFilters() {
        Natural page = new Natural(0);
        Natural size = new Natural(10);

        when(mapper.dtoSearchUser(user.getCpf(), user.getName(), user.getEmail(), user.getPhoneNumber())).thenReturn(dtoSearchUser);
        when(repository.searchUsers(dtoSearchUser, page, size)).thenReturn(List.of(user));
        when(mapper.toDTOReturnUser(user)).thenReturn(dtoReturnUser);
        List<DTOReturnUser> result = searchUserUseCase.searchUsers(user.getCpf(), user.getName(), user.getEmail(), user.getPhoneNumber(), page, size);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getCpf(), result.get(0).cpf());

        verify(mapper, times(1)).dtoSearchUser(any(), any(), any(), any());
        verify(repository, times(1)).searchUsers(dtoSearchUser, page, size);
        verify(mapper, times(1)).toDTOReturnUser(user);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nenhum usuário for encontrado")
    void shouldReturnEmptyListWhenNoUsersFound() {
        Natural page = new Natural(0);
        Natural size = new Natural(10);

        when(mapper.dtoSearchUser(any(), any(), any(), any())).thenReturn(dtoSearchUser);
        when(repository.searchUsers(any(), any(), any())).thenReturn(List.of());

        List<DTOReturnUser> result = searchUserUseCase.searchUsers(user.getCpf(), user.getName(), user.getEmail(), user.getPhoneNumber(), page, size);

        assertTrue(result.isEmpty());
        verify(repository, times(1)).searchUsers(any(), any(), any());
        verify(mapper, never()).toDTOReturnUser(any());
    }
}