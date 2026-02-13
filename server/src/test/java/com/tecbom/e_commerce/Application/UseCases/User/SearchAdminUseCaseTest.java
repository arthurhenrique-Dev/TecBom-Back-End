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

class SearchAdminUseCaseTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private SearchAdminUseCase searchAdminUseCase;

    private User user;
    private DTOReturnUser dtoReturnUser;
    private DTOSearchUser dtoSearchUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);;
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
    @DisplayName("Deve buscar administradores com filtros e paginação")
    void shouldSearchAdminsWithFilters() {
        Natural page = new Natural(0);
        Natural size = new Natural(10);

        when(mapper.dtoSearchUser(user.getCpf(), user.getName(), user.getEmail(), user.getPhoneNumber())).thenReturn(dtoSearchUser);
        when(repository.searchAdmins(dtoSearchUser, page, size)).thenReturn(List.of(user));
        when(mapper.toDTOReturnUser(user)).thenReturn(dtoReturnUser);
        List<DTOReturnUser> result = searchAdminUseCase.searchAdmins(user.getCpf(), user.getName(), user.getEmail(), user.getPhoneNumber(), page, size);
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(user.getCpf(), result.get(0).cpf());
        verify(mapper, times(1)).dtoSearchUser(any(), any(), any(), any());
        verify(repository, times(1)).searchAdmins(dtoSearchUser, page, size);
        verify(mapper, times(1)).toDTOReturnUser(user);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nenhum administrador for encontrado")
    void shouldReturnEmptyListWhenNoAdminsFound() {
        Natural page = new Natural(0);
        Natural size = new Natural(10);

        when(mapper.dtoSearchUser(any(), any(), any(), any())).thenReturn(dtoSearchUser);
        when(repository.searchAdmins(any(), any(), any())).thenReturn(List.of());
        List<DTOReturnUser> result = searchAdminUseCase.searchAdmins(user.getCpf(), user.getName(), user.getEmail(), user.getPhoneNumber(), page, size);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).searchAdmins(any(), any(), any());
        verify(mapper, never()).toDTOReturnUser(any());
    }
}