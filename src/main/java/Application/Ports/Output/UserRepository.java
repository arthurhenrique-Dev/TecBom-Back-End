package Application.Ports.Output;

import Application.DTOs.Users.DTOSearchUser;
import Domain.Entities.Users.User;
import Domain.ValueObjects.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void saveUser(User user);
    List<User> searchUsers(DTOSearchUser dtoSearchUser);
    Optional<User> getUserByCpf(Cpf cpf);
    List<User> searchAdmins(DTOSearchUser dtoSearchUser);
}
