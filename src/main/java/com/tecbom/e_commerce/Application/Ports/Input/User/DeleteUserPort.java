package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public interface DeleteUserPort {

    void deleteUserByCpf(Cpf cpf);
}
