package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOUpdateUser;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Application.Ports.Input.User.UpdateUserPort;
import com.tecbom.e_commerce.Application.Ports.Output.CepService;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Address;

public class UpdateUserUseCase implements UpdateUserPort {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UpdateUserUseCase(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void updateUser(DTOUpdateUser dtoUpdateUser) {
        User user = repository.getUserByCpf(dtoUpdateUser.cpf())
                .orElseThrow(() -> new UserNotFoundException());
        user.UpdateUser(dtoUpdateUser.email(), dtoUpdateUser.phoneNumber(), mapper.addressByService(dtoUpdateUser.address()));
        repository.saveUser(user);
    }
}
