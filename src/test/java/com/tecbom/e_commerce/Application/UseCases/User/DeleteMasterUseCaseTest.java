package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Output.MasterRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Master;
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

class DeleteMasterUseCaseTest {

    @Mock
    private MasterRepository repository;

    @InjectMocks
    private DeleteMasterUseCase deleteMasterUseCase;

    private Master master;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        master = new ReferenceObject().NormalMaster();
    }
    @Test
    @DisplayName("Deve deletar um usuário master com sucesso")
    void sucessDeleteMasterUser() {
        when(repository.findMasterByCpf(master.getCpf())).thenReturn(Optional.of(master));
        deleteMasterUseCase.deleteMasterUser(master.getCpf());
        verify(repository, times(1)).findMasterByCpf(master.getCpf());
        verify(repository, times(1)).saveMaster(master);
        assertEquals(master.getStatus(), master.getStatus().OFF);
    }
    @Test
    @DisplayName("Deve lançar InvalidDataException quando o usuário master já estiver inativo")
    void masterAlreadyDeleted() {
        master.masterOff();
        when(repository.findMasterByCpf(master.getCpf())).thenReturn(Optional.of(master));
        assertThrows(InvalidDataException.class, () -> deleteMasterUseCase.deleteMasterUser(master.getCpf()));
        verify(repository, times(1)).findMasterByCpf(master.getCpf());
        verify(repository, times(0)).saveMaster(any(Master.class));
        assertEquals(master.getStatus(), master.getStatus().OFF);
    }
    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o usuário master não for encontrado")
    void shouldThrowUserNotFoundException() {
        when(repository.findMasterByCpf(master.getCpf())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> deleteMasterUseCase.deleteMasterUser(master.getCpf()));
        verify(repository, times(1)).findMasterByCpf(master.getCpf());
        verify(repository, times(0)).saveMaster(any(Master.class));
        assertEquals(master.getStatus(), master.getStatus().ON);
    }
}