package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Input.User.HireUserPort;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Entities.Users.Role;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;

public class HireUserUseCase implements HireUserPort {

    private final UserRepository repository;

    public HireUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void hireUser(Cpf cpf) {
        User hiredUser = repository.getUserByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        if (hiredUser.getRole() == Role.ADMIN) throw new InvalidDataException("Usuário já é admin");
        hiredUser.HireUser();
        repository.saveUser(hiredUser);
    }
}
