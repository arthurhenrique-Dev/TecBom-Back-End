package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Repositories;

import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Models.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, String> {

    @Query("SELECT u FROM UserEntity u WHERE " +
            "(:cpf IS NULL OR u.cpf = :cpf) AND " +
            "(:name IS NULL OR u.name = :name) AND " +
            "(:email IS NULL OR u.email = :email) AND " +
            "(:phoneNumber IS NULL OR u.phoneNumber = :phoneNumber) AND " +
            "u.role = 'USER'")
    List<UserEntity> searchUsers(
            @Param("cpf") String cpf,
            @Param("name") String name,
            @Param("email") String email,
            @Param("phone_number") String phoneNumber,
            Pageable pageable
    );

    @Query("SELECT u FROM UserEntity u WHERE " +
            "(:cpf IS NULL OR u.cpf = :cpf) AND " +
            "(:name IS NULL OR u.name = :name) AND " +
            "(:email IS NULL OR u.email = :email) AND " +
            "(:phoneNumber IS NULL OR u.phoneNumber = :phoneNumber)")
    List<UserEntity> searchAdmins(
            @Param("cpf") String cpf,
            @Param("name") String name,
            @Param("email") String email,
            @Param("phone_number") String phoneNumber,
            Pageable pageable
    );

    @Query("SELECT u FROM UserEntity u WHERE u.cpf = :cpf")
    Optional<UserEntity> getUserByCpf(@Param("cpf") String cpfCrypt);
}
