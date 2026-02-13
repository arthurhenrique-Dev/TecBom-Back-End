package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOUpdatePasswordUser;

public interface ConfirmPasswordTokenPort {

    void confirmToken(DTOUpdatePasswordUser dtoUpdatePasswordUser);
}
