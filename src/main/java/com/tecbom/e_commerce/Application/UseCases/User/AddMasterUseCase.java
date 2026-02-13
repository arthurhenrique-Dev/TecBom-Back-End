package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSignInMaster;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Application.Ports.Input.User.AddMasterPort;
import com.tecbom.e_commerce.Application.Ports.Output.MasterRepository;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;

import java.util.Optional;

public class AddMasterUseCase implements AddMasterPort {

    private final MasterRepository repository;
    private final UserMapper mapper;

    public AddMasterUseCase(MasterRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void saveMaster(DTOSignInMaster master) {
        Optional existingMaster = repository.findMasterByCpf(master.cpf());
        if (existingMaster.isEmpty()) {
            repository.saveMaster(mapper.registerMaster(master));
        } else throw new InvalidDataException("Usuario existente");
    }
}
