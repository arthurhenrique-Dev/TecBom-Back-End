package Application.UseCases.User;

import Application.DTOs.Users.DTOSaveUser;
import Application.Ports.Input.User.SaveAdminPort;
import Application.Ports.Output.EmailService;
import Application.Ports.Output.UserRepository;
import Application.Mappers.Users.UserMapper;
import Domain.Entities.Users.User;
import Domain.Exceptions.Exceptions.ValidationFailedException;

import java.util.Optional;

public class SaveAdminUseCase implements SaveAdminPort {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final EmailService service;

    public SaveAdminUseCase(UserRepository repository, UserMapper mapper, EmailService service) {
        this.repository = repository;
        this.mapper = mapper;
        this.service = service;
    }

    @Override
    public void saveAdmin(DTOSaveUser dtoSaveUser) {
        Optional<User> existingUser = repository.getUserByCpf(dtoSaveUser.cpf());
        if (existingUser.isPresent()) throw new ValidationFailedException("Usuario existente");
        User readyToSave = mapper.registerAdmin(dtoSaveUser);
        repository.saveUser(readyToSave);
        service.ValidateEmail(readyToSave.getEmail(), readyToSave.getEmailValidation().token());
    }
}
