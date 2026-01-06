package Application.UseCases.User;

import Application.DTOs.Users.DTOReturnUser;
import Application.DTOs.Users.DTOSearchUser;
import Application.Ports.Input.User.SearchAdminPort;
import Application.Ports.Output.UserRepository;
import Application.Mappers.Users.UserMapper;
import Domain.Entities.Users.User;

import java.util.List;

public class SearchAdminUseCase implements SearchAdminPort {

    private final UserRepository repository;
    private final UserMapper mapper;

    public SearchAdminUseCase(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<DTOReturnUser> searchAdmins(DTOSearchUser dtoSearchUser) {
        List<User> users = repository.searchAdmins(dtoSearchUser);
        return mapper.toDTOReturnUser(users);
    }
}
