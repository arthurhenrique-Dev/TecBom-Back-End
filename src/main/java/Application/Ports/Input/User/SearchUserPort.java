package Application.Ports.Input.User;

import Application.DTOs.Users.DTOReturnUser;
import Application.DTOs.Users.DTOSearchUser;

import java.util.List;

public interface SearchUserPort {

    List<DTOReturnUser> searchUsers(DTOSearchUser dtoSearchUser);
}
