package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSaveUser;

public interface SaveUserPort {

    void saveUser(DTOSaveUser dtoSaveUser);
}
