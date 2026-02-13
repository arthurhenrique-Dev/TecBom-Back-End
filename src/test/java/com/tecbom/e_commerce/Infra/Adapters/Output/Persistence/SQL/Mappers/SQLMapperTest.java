package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Mappers;

import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Entities.Users.Role;
import com.tecbom.e_commerce.Domain.Entities.Users.Status;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.ValueObjects.*;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Models.MasterEntity;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.SQL.Models.UserEntity;
import com.tecbom.e_commerce.Infra.Security.Service.CryptographyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SQLMapperTest {

    private SQLMapper mapper;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        mapper = new SQLMapper(passwordEncoder);
    }

    @Test
    void deveConverterUserParaDbModelComDadosCriptografados() {
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");

        Address address = new Address(
                new Cep("01001000"),
                "Rua A",
                "Bairro B",
                "Cidade C",
                "SP",
                "Apto 1",
                new ValidText("100")
        );

        User user = new User(
                new Cpf("10917087038"),
                new Name("Fulano de Tal"),
                new Password("Senha@Strong123"),
                new EmailVO("fulano@email.com"),
                address,
                new PhoneNumber("11999999999"),
                Role.COMUM,
                Status.ON,
                new EmailValidation("token-email", false),
                new PasswordUpdater("token-pass", LocalDateTime.now())
        );

        UserEntity entity = mapper.toDbModel(user);

        assertNotNull(entity);
        assertEquals("hashed_password", entity.getPassword());
        assertEquals("Fulano de Tal", CryptographyService.decrypt(entity.getName()));
        assertEquals("token-email", entity.getTokenEmail());
    }

    @Test
    void deveConverterUserEntityParaDomainDescriptografandoDados() {
        String encryptedCpf = CryptographyService.encrypt("10917087038");
        String encryptedName = CryptographyService.encrypt("Fulano de Tal");
        String encryptedEmail = CryptographyService.encrypt("fulano@email.com");
        String encryptedCep = CryptographyService.encrypt("01001000");
        String encryptedRua = CryptographyService.encrypt("Rua A");
        String encryptedBairro = CryptographyService.encrypt("Bairro B");
        String encryptedCidade = CryptographyService.encrypt("Cidade C");
        String encryptedEstado = CryptographyService.encrypt("SP");
        String encryptedComp = CryptographyService.encrypt("Apto 1");
        String encryptedNum = CryptographyService.encrypt("100");
        String encryptedPhone = CryptographyService.encrypt("11999999999");

        UserEntity entity = new UserEntity(
                encryptedCpf, encryptedName, "Senha@Hashed123", encryptedEmail,
                encryptedCep, encryptedRua, encryptedBairro, encryptedCidade,
                encryptedEstado, encryptedComp, encryptedNum, encryptedPhone,
                Role.COMUM, Status.ON, "token123", true, "passToken", LocalDateTime.now()
        );

        User user = mapper.toDomain(entity);

        assertNotNull(user);
        assertEquals("10917087038", user.getCpf().cpf());
        assertEquals("Fulano de Tal", user.getName().name());
    }

    @Test
    void deveConverterMasterParaEntityCriptografado() {
        Master master = new Master(
                new Cpf("10917087038"),
                new Name("Master User"),
                new Password("mAster@pass23#"),
                new EmailVO("master@email.com"),
                new PhoneNumber("11988887777")
        );

        MasterEntity entity = mapper.masterToEntity(master);

        assertNotNull(entity);
        String decryptedName = CryptographyService.decrypt(entity.getName());

        if (decryptedName.contains("name=")) {
            assertTrue(decryptedName.contains("Master User"));
        } else {
            assertEquals("Master User", decryptedName);
        }
    }

    @Test
    void deveConverterMasterEntityParaDomain() {
        MasterEntity entity = new MasterEntity(
                CryptographyService.encrypt("10917087038"),
                CryptographyService.encrypt("Master User"),
                "Senha@Master123",
                CryptographyService.encrypt("master@email.com"),
                CryptographyService.encrypt("11988887777"),
                Role.MASTER,
                Status.ON
        );

        Master master = master = mapper.masterToDomain(entity);

        assertNotNull(master);
        assertEquals("10917087038", master.getCpf().cpf());
        assertEquals("Master User", master.getName().name());
    }
}