package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOReturnUser;
import com.tecbom.e_commerce.Application.DTOs.Users.DTOSearchUser;
import com.tecbom.e_commerce.Domain.ValueObjects.*;

import java.util.List;

public interface SearchAdminPort {

    List<DTOReturnUser> searchAdmins(Cpf cpf, Name name, EmailVO email, PhoneNumber phoneNumber, Natural page, Natural size);
}
