package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Adapters;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOSearchUser;
import com.tecbom.e_commerce.Application.Ports.Output.UserRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.DatabaseFailedException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;
import com.tecbom.e_commerce.Domain.ValueObjects.Natural;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Mappers.SQLMapper;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Models.UserEntity;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Repositories.JpaUserRepository;
import com.tecbom.e_commerce.Infra.Security.Service.CryptographyService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {

    private final JpaUserRepository frameworkRepository;
    private final SQLMapper mapper;

    public UserRepositoryAdapter(JpaUserRepository frameworkRepository, SQLMapper mapper) {
        this.frameworkRepository = frameworkRepository;
        this.mapper = mapper;
    }

    private EncryptedSearchFilters encryptFilters(DTOSearchUser dto) {
        return new EncryptedSearchFilters(
                (dto.cpf() != null) ? CryptographyService.encrypt(dto.cpf().toString()) : null,
                (dto.name() != null) ? CryptographyService.encrypt(dto.name().toString()) : null,
                (dto.email() != null) ? CryptographyService.encrypt(dto.email().toString()) : null,
                (dto.phoneNumber() != null) ? CryptographyService.encrypt(dto.phoneNumber().toString()) : null
        );
    }

    @Override
    public void saveUser(User user) {
        try {
            UserEntity userEntity = mapper.toDbModel(user);
            frameworkRepository.save(userEntity);
        } catch (Exception e) {
            throw new DatabaseFailedException();
        }

    }

    @Override
    public List<User> searchUsers(DTOSearchUser dtoSearchUser, Natural pages, Natural size) {
        try {
            Pageable pageable = PageRequest.of(pages.i(), size.i());

            EncryptedSearchFilters filters = encryptFilters(dtoSearchUser);

            List<UserEntity> entities = frameworkRepository.searchUsers(filters.cpf(), filters.name(), filters.email(), filters.phone(), pageable);

            return entities.stream()
                    .map(mapper::toDomain)
                    .toList();
        } catch (Exception e) {
            throw new DatabaseFailedException();
        }
    }

    @Override
    public Optional<User> getUserByCpf(Cpf cpf) {
        try {
            String cpfCrypt = CryptographyService.encrypt(cpf.toString());
            return frameworkRepository.getUserByCpf(cpfCrypt)
                    .map(mapper::toDomain);
        } catch (Exception e) {
            throw new DatabaseFailedException();
        }
    }

    @Override
    public List<User> searchAdmins(DTOSearchUser dtoSearchUser, Natural pages, Natural size) {
        try {
            Pageable pageable = PageRequest.of(pages.i(), size.i());

            EncryptedSearchFilters filters = encryptFilters(dtoSearchUser);

            List<UserEntity> entities = frameworkRepository.searchAdmins(filters.cpf(), filters.name(), filters.email(), filters.phone(), pageable);

            return entities.stream()
                    .map(mapper::toDomain)
                    .toList();
        } catch (Exception e) {
            throw new DatabaseFailedException();
        }
    }

    private record EncryptedSearchFilters(
            String cpf,
            String name,
            String email,
            String phone
    ) {
    }
}
