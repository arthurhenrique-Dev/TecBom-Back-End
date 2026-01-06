package Application.UseCases.User;

import Application.DTOs.Users.DTOReturnUser;
import Application.DTOs.Users.DTOSearchUser;
import Application.Mappers.Users.UserMapper;
import Application.Ports.Input.User.SearchUserPort;
import Application.Ports.Output.UserRepository;
import Domain.Entities.Users.User;

import java.util.List;

public class SearchUserUseCase implements SearchUserPort {

    private final UserRepository repository;
    private final UserMapper mapper;

    public SearchUserUseCase(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<DTOReturnUser> searchUsers(DTOSearchUser dtoSearchUser) {
        List<User> users = repository.searchUsers(dtoSearchUser);
        return mapper.toDTOReturnUser(users);
    }
}
