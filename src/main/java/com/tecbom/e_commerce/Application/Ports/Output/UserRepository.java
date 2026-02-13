package com.tecbom.e_commerce.Application.Ports.Output;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSearchUser;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.ValueObjects.*;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    void saveUser(User user);

    List<User> searchUsers(DTOSearchUser dtoSearchUser, Natural pages, Natural size);

    Optional<User> getUserByCpf(Cpf cpf);

    List<User> searchAdmins(DTOSearchUser dtoSearchUser, Natural pages, Natural size);
}
