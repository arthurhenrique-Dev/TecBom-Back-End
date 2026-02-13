package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Input.User.MasterOnUseCasePort;
import com.tecbom.e_commerce.Application.Ports.Output.MasterRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public class MasterOnUseCase implements MasterOnUseCasePort {

    private final MasterRepository repository;

    public MasterOnUseCase(MasterRepository repository) {
        this.repository = repository;
    }

    @Override
    public void masterOn(Cpf cpf) {
        Master master = repository.findMasterByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        master.masterOn();
        repository.saveMaster(master);
    }
}
