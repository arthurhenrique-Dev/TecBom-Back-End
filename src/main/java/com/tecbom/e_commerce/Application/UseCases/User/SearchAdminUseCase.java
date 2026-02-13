package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOReturnUser;
import com.tecbom.e_commerce.Application.DTOs.Users.DTOSearchUser;
import com.tecbom.e_commerce.Application.Ports.Input.User.SearchAdminPort;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Application.Mappers.UserMapper;
import com.tecbom.e_commerce.Domain.ValueObjects.*;

import java.util.List;

public class SearchAdminUseCase implements SearchAdminPort {

    private final UserRepository repository;
    private final UserMapper mapper;

    public SearchAdminUseCase(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<DTOReturnUser> searchAdmins(Cpf cpf, Name name, EmailVO email, PhoneNumber phoneNumber, Natural pages, Natural size) {
        DTOSearchUser searchUser = mapper.dtoSearchUser(cpf, name, email, phoneNumber);

        return repository.searchAdmins(searchUser, pages, size)
                .stream()
                .map(mapper::toDTOReturnUser)
                .toList();
    }
}
