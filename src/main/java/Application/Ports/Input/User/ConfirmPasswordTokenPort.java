package Application.Ports.Input.User;

import Application.DTOs.Users.DTOUpdatePasswordUser;

public interface ConfirmPasswordTokenPort {

    void confirmToken(DTOUpdatePasswordUser dtoUpdatePasswordUser);
}
