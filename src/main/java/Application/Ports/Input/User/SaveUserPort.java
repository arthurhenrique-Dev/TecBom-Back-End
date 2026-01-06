package Application.Ports.Input.User;

import Application.DTOs.Users.DTOSaveUser;

public interface SaveUserPort {

    void saveUser(DTOSaveUser dtoSaveUser);
}
