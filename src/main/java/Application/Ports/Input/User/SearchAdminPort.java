package Application.Ports.Input.User;

import Application.DTOs.Users.DTOReturnUser;
import Application.DTOs.Users.DTOSearchUser;

import java.util.List;

public interface SearchAdminPort {

    List<DTOReturnUser> searchAdmins(DTOSearchUser dtoSearchUser);
}
