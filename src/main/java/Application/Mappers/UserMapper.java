package Application.Mappers.Users;

import Application.DTOs.Users.DTOReturnUser;
import Application.DTOs.Users.DTOSaveUser;
import Domain.Entities.Users.*;

import java.util.List;

public class UserMapper {

    public DTOReturnUser toDTOReturnUser(User user) {
        DTOReturnUser dtoReturnUser = new DTOReturnUser(
            user.getCpf(),
            user.getName(),
            user.getEmail(),
            user.getPhoneNumber(),
            user.getAddress(),
            user.getCart()
        );
        return dtoReturnUser;
    }
    public User registerUser(DTOSaveUser dtoSaveUser) {
        User user = new User(
            dtoSaveUser.cpf(),
            dtoSaveUser.name(),
            dtoSaveUser.password(),
            dtoSaveUser.email(),
            dtoSaveUser.address(),
            dtoSaveUser.phoneNumber(),
            Role.COMUM,
            Status.VALIDATING
        );
        return user;
    }

    public User registerAdmin(DTOSaveUser dtoSaveUser) {
        User admin = new User(
                dtoSaveUser.cpf(),
                dtoSaveUser.name(),
                dtoSaveUser.password(),
                dtoSaveUser.email(),
                dtoSaveUser.address(),
                dtoSaveUser.phoneNumber(),
                Role.ADMIN,
                Status.VALIDATING
        );
        return admin;
    }
    public List<DTOReturnUser> toDTOReturnUser(List<User> users) {
        return users.stream().map(this::toDTOReturnUser).toList();
    }
}
