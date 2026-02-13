package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSaveUser;

public interface SaveAdminPort {

    void saveAdmin(DTOSaveUser dtoSaveUser);
}
