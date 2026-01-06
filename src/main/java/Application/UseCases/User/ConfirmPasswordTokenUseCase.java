package Application.UseCases.User;

import Application.DTOs.Users.DTOUpdatePasswordUser;
import Application.Ports.Input.User.ConfirmPasswordTokenPort;
import Application.Ports.Output.UserRepository;
import Domain.Entities.Users.User;
import Domain.Exceptions.Exceptions.ValidationFailedException;

import java.time.LocalDateTime;

public class ConfirmPasswordTokenUseCase implements ConfirmPasswordTokenPort {

    private UserRepository repository;

    @Override
    public void confirmToken(DTOUpdatePasswordUser dtoUpdatePasswordUser) {
        User user = repository.getUserByCpf(dtoUpdatePasswordUser.cpf())
                .orElseThrow(() -> new ValidationFailedException());
        if (user.getPasswordUpdater().expirationDate().isBefore(LocalDateTime.now())){
            user.getPasswordUpdater().Stop();
            repository.saveUser(user);
            throw new ValidationFailedException("Token expirado");
        } else {
            user.ChangePassword(dtoUpdatePasswordUser.token(), dtoUpdatePasswordUser.password());
            repository.saveUser(user);
        }
    }
}
