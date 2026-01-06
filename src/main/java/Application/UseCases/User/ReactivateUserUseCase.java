package Application.UseCases.User;

import Application.Ports.Input.User.ReactivateUserPort;
import Application.Ports.Output.UserRepository;
import Domain.Entities.Users.User;
import Domain.Entities.Users.Status;
import Domain.Exceptions.Exceptions.InvalidDataException;
import Domain.Exceptions.Exceptions.UserNotFoundException;
import Domain.ValueObjects.Cpf;

public class ReactivateUserUseCase implements ReactivateUserPort {

    private final UserRepository repository;

    public ReactivateUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void reactivateUser(Cpf cpf) {
        User user = repository.getUserByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        if (user.getStatus() == Status.OFF) {
            user.Reactivate();
            repository.saveUser(user);
        }
        else throw new InvalidDataException("Usuario não necessita de reativação");
    }
}
