package Application.UseCases.User;

import Application.Ports.Input.User.HireUserPort;
import Application.Ports.Output.UserRepository;
import Domain.Entities.Users.User;
import Domain.Entities.Users.Role;
import Domain.Exceptions.Exceptions.InvalidDataException;
import Domain.Exceptions.Exceptions.UserNotFoundException;
import Domain.ValueObjects.Cpf;

public class HireUserUseCase implements HireUserPort {

    private final UserRepository repository;

    public HireUserUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void hireUser(Cpf cpf) {
        User hiredUser = repository.getUserByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        if (hiredUser.getRole() == Role.ADMIN) throw new InvalidDataException("Usuário já é admin");
        hiredUser.HireUser();
        repository.saveUser(hiredUser);
    }
}
