package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOReturnUser;
import com.tecbom.e_commerce.Application.DTOs.Users.DTOSearchUser;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Application.Ports.Input.User.SearchUserPort;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.ValueObjects.*;

import java.util.List;

public class SearchUserUseCase implements SearchUserPort {

    private final UserRepository repository;
    private final UserMapper mapper;

    public SearchUserUseCase(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<DTOReturnUser> searchUsers(Cpf cpf, Name name, EmailVO email, PhoneNumber phoneNumber, Natural pages, Natural size) {
        DTOSearchUser searchUser = mapper.dtoSearchUser(cpf, name, email, phoneNumber);

        return repository.searchUsers(searchUser, pages, size)
                .stream()
                .map(mapper::toDTOReturnUser)
                .toList();
    }
}
