package Application.UseCases.User;

import Application.Ports.Input.User.DeleteUserPort;
import Application.Ports.Output.UserRepository;
import Domain.Entities.Users.User;
import Domain.Entities.Users.Status;
import Domain.Exceptions.Exceptions.InvalidDataException;
import Domain.Exceptions.Exceptions.UserNotFoundException;
import Domain.ValueObjects.Cpf;


public class DeleteUserUseCase implements DeleteUserPort {

    private final UserRepository repository;

    public DeleteUserUseCase(UserRepository repository) {
        this.repository = DeleteUserUseCase.this.repository;
    }

    @Override
    public void deleteUserByCpf(Cpf cpf) {
        User existingUser = repository.getUserByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        if (existingUser.getStatus() == Status.ON) {
            existingUser.Deactivate();
            repository.saveUser(existingUser);
        }
        else throw new InvalidDataException("Usuário já inativo");
    }
}
