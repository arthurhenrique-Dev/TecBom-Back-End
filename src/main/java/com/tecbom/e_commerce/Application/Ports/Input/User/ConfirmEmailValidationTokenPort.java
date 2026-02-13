package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOEmailValidation;

public interface ConfirmEmailValidationTokenPort {

    void confirmEmailValidationToken(DTOEmailValidation dtoEmailValidation);
}
