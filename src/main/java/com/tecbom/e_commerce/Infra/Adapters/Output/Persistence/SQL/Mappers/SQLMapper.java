package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Mappers;

import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.ValueObjects.*;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Models.MasterEntity;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Models.UserEntity;
import com.tecbom.e_commerce.Infra.Security.Service.CryptographyService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SQLMapper {

    private final PasswordEncoder passwordEncoder;

    public SQLMapper(@Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity toDbModel(User user) {

        String cpfEncrypt = CryptographyService.encrypt(user.getCpf().cpf().toString());
        String nameEncrypt = CryptographyService.encrypt(user.getName().name().toString());
        String emailEncrypt = CryptographyService.encrypt(user.getEmail().email().toString());
        String passwordEncrypt = passwordEncoder.encode(user.getPassword().password());
        String cepEncrypt = CryptographyService.encrypt(user.getAddress().cep().toString());
        String ruaEncrypt = CryptographyService.encrypt(user.getAddress().rua().toString());
        String bairroEncrypt = CryptographyService.encrypt(user.getAddress().bairro().toString());
        String cidadeEncrypt = CryptographyService.encrypt(user.getAddress().cidade().toString());
        String estadoEncrypt = CryptographyService.encrypt(user.getAddress().estado().toString());
        String complementoEncrypt = CryptographyService.encrypt(user.getAddress().complemento().toString());
        String numeroEncrypt = CryptographyService.encrypt(user.getAddress().numero().toString());
        String phoneNumberEncrypt = CryptographyService.encrypt(user.getPhoneNumber().toString());

        UserEntity userEntity = new UserEntity(
                cpfEncrypt,
                nameEncrypt,
                passwordEncrypt,
                emailEncrypt,
                cepEncrypt,
                ruaEncrypt,
                bairroEncrypt,
                cidadeEncrypt,
                estadoEncrypt,
                complementoEncrypt,
                numeroEncrypt,
                phoneNumberEncrypt,
                user.getRole(),
                user.getStatus(),
                user.getEmailValidation().token(),
                user.getEmailValidation().validated(),
                user.getPasswordUpdater().token(),
                user.getPasswordUpdater().expirationDate()
        );
        return userEntity;
    }

    public User toDomain(UserEntity entity) {

        Cpf cpfDecrypt = new Cpf(CryptographyService.decrypt(entity.getCpf().toString()));
        Name nameDecrypt = new Name(CryptographyService.decrypt(entity.getName().toString()));
        Password passwordEncrypted = new Password(entity.getPassword());
        EmailVO emailDecrypt = new EmailVO(CryptographyService.decrypt(entity.getEmail().toString()));
        Cep cepDecrypt = new Cep(CryptographyService.decrypt(entity.getCep().toString()));
        String ruaDecrypt = CryptographyService.decrypt(entity.getRua().toString());
        String bairroDecrypt = CryptographyService.decrypt(entity.getBairro().toString());
        String cidadeDecrypt = CryptographyService.decrypt(entity.getCidade().toString());
        String estadoDecrypt = CryptographyService.decrypt(entity.getEstado().toString());
        String complementoDecrypt = CryptographyService.decrypt(entity.getComplemento().toString());
        ValidText numeroDecrypt = new ValidText(CryptographyService.decrypt(entity.getNumero().toString()));
        Address addressDecrypt = new Address(
                cepDecrypt,
                ruaDecrypt,
                bairroDecrypt,
                cidadeDecrypt,
                estadoDecrypt,
                complementoDecrypt,
                numeroDecrypt
        );
        PhoneNumber phoneNumberDecrypt = new PhoneNumber(CryptographyService.decrypt(entity.getPhoneNumber().toString()));
        EmailValidation emailValidationDecrypt = new EmailValidation(entity.getTokenEmail(), entity.isValidated());
        PasswordUpdater passwordUpdaterDecrypt = new PasswordUpdater(entity.getTokenPassword(), entity.getExpirationDate());

        User user = new User(
                cpfDecrypt,
                nameDecrypt,
                passwordEncrypted,
                emailDecrypt,
                addressDecrypt,
                phoneNumberDecrypt,
                entity.getRole(),
                entity.getStatus(),
                emailValidationDecrypt,
                passwordUpdaterDecrypt
        );
        return user;
    }

    public MasterEntity masterToEntity(Master master) {

        String cpfEncrypt = CryptographyService.encrypt(master.getCpf().toString());
        String nameEncrypt = CryptographyService.encrypt(master.getName().toString());
        String passwordEncrypt = new BCryptPasswordEncoder().encode(master.getPassword().toString());
        String emailEncrypt = CryptographyService.encrypt(master.getEmail().toString());
        String phoneNumberEncrypt = CryptographyService.encrypt(master.getPhoneNumber().toString());
        MasterEntity masterEntity = new MasterEntity(
                cpfEncrypt,
                nameEncrypt,
                passwordEncrypt,
                emailEncrypt,
                phoneNumberEncrypt,
                master.getRole(),
                master.getStatus()
        );
        return masterEntity;
    }

    public Master masterToDomain(MasterEntity masterEntity) {

        Cpf cpfDecrypt = new Cpf(CryptographyService.decrypt(masterEntity.getCpf().toString()));
        Name nameDecrypt = new Name(CryptographyService.decrypt(masterEntity.getName().toString()));
        Password passwordEncrypted = new Password(masterEntity.getPassword());
        EmailVO emailDecrypt = new EmailVO(CryptographyService.decrypt(masterEntity.getEmail().toString()));
        PhoneNumber phoneNumberDecrypt = new PhoneNumber(CryptographyService.decrypt(masterEntity.getPhoneNumber().toString()));

        Master master = new Master(
                cpfDecrypt,
                nameDecrypt,
                passwordEncrypted,
                emailDecrypt,
                phoneNumberDecrypt,
                masterEntity.getRole(),
                masterEntity.getStatus()
        );
        return master;
    }
}
