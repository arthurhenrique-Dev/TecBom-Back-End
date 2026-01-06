package Application.Ports.Input.User;

import Application.DTOs.Users.DTOEmailValidation;

public interface ConfirmEmailValidationTokenPort {

    void confirmEmailValidationToken(DTOEmailValidation dtoEmailValidation);
}
