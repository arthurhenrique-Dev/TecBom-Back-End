package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSignInMaster;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Application.Ports.Output.MasterRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AddMasterUseCaseTest {

    @Mock
    private MasterRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private AddMasterUseCase addMasterUseCase;

    private Master master;

    private DTOSignInMaster dtoSignInMaster;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        master = new ReferenceObject().NormalMaster();
        dtoSignInMaster = new ReferenceObject().dtoSignInMaster();
    }
    @Test
    @DisplayName("Deve salvar um master com sucesso")
    void sucessSaveMaster() {
        when(repository.findMasterByCpf(dtoSignInMaster.cpf())).thenReturn(Optional.empty());
        addMasterUseCase.saveMaster(dtoSignInMaster);
        verify(repository, times(1)).saveMaster(mapper.registerMaster(dtoSignInMaster));
    }
    @Test
    @DisplayName("Deve lançar uma exceção ao tentar salvar um master já existente")
    void failSaveMaster() {
        when(repository.findMasterByCpf(dtoSignInMaster.cpf())).thenReturn(Optional.of(master));
        assertThrows(InvalidDataException.class, () -> addMasterUseCase.saveMaster(dtoSignInMaster));
        verify(repository, times(0)).saveMaster(mapper.registerMaster(dtoSignInMaster));
    }
}