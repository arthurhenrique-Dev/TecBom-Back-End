package Application.UseCases.User;

import Application.DTOs.Users.DTOSaveUser;
import Application.Mappers.Users.UserMapper;
import Application.Ports.Input.User.SaveUserPort;
import Application.Ports.Output.EmailService;
import Application.Ports.Output.UserRepository;
import Domain.Entities.Users.User;
import Domain.Exceptions.Exceptions.ValidationFailedException;

import java.util.Optional;

public class SaveUserUseCase implements SaveUserPort {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final EmailService service;

    public SaveUserUseCase(UserRepository repository, UserMapper mapper, EmailService service) {
        this.repository = repository;
        this.mapper = mapper;
        this.service = service;
    }

    @Override
    public void saveUser(DTOSaveUser dtoSaveUser) {
        Optional<User> existingUser = repository.getUserByCpf(dtoSaveUser.cpf());
        if (existingUser.isPresent()) throw new ValidationFailedException("Usuario existente");
        User readyToSave = mapper.registerUser(dtoSaveUser);
        repository.saveUser(readyToSave);
        service.ValidateEmail(readyToSave.getEmail(), readyToSave.getEmailValidation().token());
    }
}
