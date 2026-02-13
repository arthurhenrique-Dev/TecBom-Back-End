package com.tecbom.e_commerce.Application.Mappers;

import com.tecbom.e_commerce.Application.DTOs.Users.*;
import com.tecbom.e_commerce.Application.Ports.Output.CepService;
import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Entities.Users.Role;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.ValueObjects.*;

import java.util.List;

public class UserMapper {

    private final CepService service;

    public UserMapper(CepService service) {
        this.service = service;
    }

    public DTOReturnUser toDTOReturnUser(User user) {
        DTOReturnUser dtoReturnUser = new DTOReturnUser(
                user.getCpf(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress()
        );
        return dtoReturnUser;
    }

    public Address addressByService(DTOSaveAddress dtoSaveAddress) {
        DTOReturnCepService dtoReturnCepService = service.getAddressByCep(dtoSaveAddress.cep());
        Address address = new Address(
                dtoSaveAddress.cep(),
                dtoReturnCepService.rua(),
                dtoReturnCepService.bairro(),
                dtoReturnCepService.cidade(),
                dtoReturnCepService.estado(),
                dtoSaveAddress.complemento(),
                dtoSaveAddress.numero()
        );
        return address;
    }
    public User registerUser(DTOSaveUser dtoSaveUser) {
        Address address = addressByService(dtoSaveUser.address());
        User user = new User(
                dtoSaveUser.cpf(),
                dtoSaveUser.name(),
                dtoSaveUser.password(),
                dtoSaveUser.email(),
                address,
                dtoSaveUser.phoneNumber(),
                Role.COMUM
        );
        return user;
    }

    public User registerAdmin(DTOSaveUser dtoSaveUser) {
        DTOReturnCepService dtoReturnCepService = service.getAddressByCep(dtoSaveUser.address().cep());
        Address address = new Address(
                dtoSaveUser.address().cep(),
                dtoReturnCepService.rua(),
                dtoReturnCepService.bairro(),
                dtoReturnCepService.cidade(),
                dtoReturnCepService.estado(),
                dtoSaveUser.address().complemento(),
                dtoSaveUser.address().numero()
        );

        User admin = new User(
                dtoSaveUser.cpf(),
                dtoSaveUser.name(),
                dtoSaveUser.password(),
                dtoSaveUser.email(),
                address,
                dtoSaveUser.phoneNumber(),
                Role.ADMIN
        );
        return admin;
    }

    public Master registerMaster(DTOSignInMaster dtoSignInMaster) {
        Master master = new Master(
                dtoSignInMaster.cpf(),
                dtoSignInMaster.name(),
                dtoSignInMaster.plainPassword(),
                dtoSignInMaster.email(),
                dtoSignInMaster.phoneNumber()
        );
        return master;
    }

    public DTOSearchUser dtoSearchUser(Cpf cpf, Name name, EmailVO email, PhoneNumber phoneNumber){
        return new DTOSearchUser(
                cpf,
                name,
                email,
                phoneNumber);
    }
}
