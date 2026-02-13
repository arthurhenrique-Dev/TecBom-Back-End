package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOUpdateUser;

public interface UpdateUserPort {

    void updateUser(DTOUpdateUser dtoUpdateUser);
}
