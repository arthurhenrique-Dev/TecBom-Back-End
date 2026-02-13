package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Output.MasterRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Entities.Users.Status;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MasterOnUseCaseTest {

    @Mock
    private MasterRepository repository;

    @InjectMocks
    private MasterOnUseCase masterOnUseCase;

    private Master master;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        master = new ReferenceObject().NormalMaster();
    }

    @Test
    @DisplayName("Deve reativar um usuário master com sucesso")
    void sucessMasterOn() {
        master.masterOff();
        when(repository.findMasterByCpf(master.getCpf())).thenReturn(java.util.Optional.of(master));
        assertEquals(master.getStatus(), Status.OFF);
        masterOnUseCase.masterOn(master.getCpf());
        verify(repository, times(1)).findMasterByCpf(master.getCpf());
        assertEquals(master.getStatus(), master.getStatus().ON);
    }

    @Test
    @DisplayName("Deve lançar InvalidDataException quando o usuário master já estiver ativo")
    void masterAlreadyOn() {
        when(repository.findMasterByCpf(master.getCpf())).thenReturn(java.util.Optional.of(master));
        assertEquals(master.getStatus(), master.getStatus().ON);
        assertThrows(InvalidDataException.class, () -> masterOnUseCase.masterOn(master.getCpf()));
        verify(repository, times(1)).findMasterByCpf(master.getCpf());
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o usuário master não for encontrado")
    void masterNotFound() {
        when(repository.findMasterByCpf(master.getCpf())).thenReturn(java.util.Optional.empty());
        assertThrows(UserNotFoundException.class, () -> masterOnUseCase.masterOn(master.getCpf()));
        verify(repository, times(1)).findMasterByCpf(master.getCpf());
    }
}