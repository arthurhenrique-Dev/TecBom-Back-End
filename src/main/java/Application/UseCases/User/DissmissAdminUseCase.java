package Application.UseCases.User;

import Application.Ports.Input.User.DissmissAdminPort;
import Application.Ports.Output.UserRepository;
import Domain.Entities.Users.User;
import Domain.Entities.Users.Role;
import Domain.Exceptions.Exceptions.InvalidDataException;
import Domain.Exceptions.Exceptions.UserNotFoundException;
import Domain.ValueObjects.Cpf;

public class DissmissAdminUseCase implements DissmissAdminPort {

    private final UserRepository repository;

    public DissmissAdminUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void dissmissAdmin(Cpf cpf) {
        User dissmissedAdmin = repository.getUserByCpf(cpf)
                .orElseThrow(() -> new UserNotFoundException());
        if (dissmissedAdmin.getRole() == Role.COMUM) throw new InvalidDataException("Usuário não é admin");
        dissmissedAdmin.HireUser();
        repository.saveUser(dissmissedAdmin);
    }
}
