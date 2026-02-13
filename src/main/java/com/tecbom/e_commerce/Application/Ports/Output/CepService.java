package com.tecbom.e_commerce.Application.Ports.Output;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOReturnCepService;
import com.tecbom.e_commerce.Domain.ValueObjects.Cep;

public interface CepService {

    DTOReturnCepService getAddressByCep(Cep cep);
}
