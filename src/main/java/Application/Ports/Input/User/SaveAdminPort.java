package Application.Ports.Input.User;

import Application.DTOs.Users.DTOSaveUser;

public interface SaveAdminPort {

    void saveAdmin(DTOSaveUser dtoSaveUser);
}
