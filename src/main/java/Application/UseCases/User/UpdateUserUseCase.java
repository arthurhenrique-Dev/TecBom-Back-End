package Application.UseCases.User;

import Application.DTOs.Users.DTOUpdateUser;
import Application.Ports.Input.User.UpdateUserPort;
import Application.Ports.Output.UserRepository;
import Domain.Entities.Users.User;
import Domain.Exceptions.Exceptions.InvalidDataException;
import Domain.Exceptions.Exceptions.UserNotFoundException;

public class UpdateUserUseCase implements UpdateUserPort {

    private final UserRepository repository;

    public UpdateUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateUser(DTOUpdateUser dtoUpdateUser) {
        User user = repository.getUserByCpf(dtoUpdateUser.cpf())
                .orElseThrow(() -> new UserNotFoundException());
        user.UpdateUser(dtoUpdateUser.email(), dtoUpdateUser.phoneNumber(), dtoUpdateUser.address());
        repository.saveUser(user);
    }
}
