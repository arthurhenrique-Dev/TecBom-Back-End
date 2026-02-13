package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Input.User.DeleteMasterPort;
import com.tecbom.e_commerce.Application.Ports.Output.MasterRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Entities.Users.Status;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public class DeleteMasterUseCase implements DeleteMasterPort {

    private final MasterRepository repository;

    public DeleteMasterUseCase(MasterRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteMasterUser(Cpf cpf) {
        Master master = repository.findMasterByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        if (master.getStatus() == Status.OFF) throw new InvalidDataException("Usuário já inativo");
        else {
            master.masterOff();
            repository.saveMaster(master);
        }
    }
}
