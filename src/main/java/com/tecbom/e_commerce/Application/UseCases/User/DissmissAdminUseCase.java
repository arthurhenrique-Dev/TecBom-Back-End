package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Input.User.DissmissAdminPort;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Entities.Users.Role;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public class DissmissAdminUseCase implements DissmissAdminPort {

    private final UserRepository repository;

    public DissmissAdminUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void dissmissAdmin(Cpf cpf) {
        User dissmissedAdmin = repository.getUserByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        if (dissmissedAdmin.getRole() == Role.COMUM) throw new InvalidDataException("Usuário não é admin");
        dissmissedAdmin.DismissAdmin();
        repository.saveUser(dissmissedAdmin);
    }
}
