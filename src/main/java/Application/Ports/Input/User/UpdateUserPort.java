package Application.Ports.Input.User;

import Application.DTOs.Users.DTOUpdateUser;

public interface UpdateUserPort {

    void updateUser(DTOUpdateUser dtoUpdateUser);
}
