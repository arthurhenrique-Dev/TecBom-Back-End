package com.tecbom.e_commerce.Application.Ports.Output;

import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

import java.util.Optional;


public interface MasterRepository {

    void saveMaster(Master master);

    Optional<Master> findMasterByCpf(Cpf cpf);
}
