package com.tecbom.e_commerce.Application.Mappers;

import com.tecbom.e_commerce.Application.DTOs.Users.*;
import com.tecbom.e_commerce.Application.Ports.Output.CepService;
import com.tecbom.e_commerce.Domain.Entities.Users.Master;
import com.tecbom.e_commerce.Domain.Entities.Users.Role;
import com.tecbom.e_commerce.Domain.Entities.Users.User;
import com.tecbom.e_commerce.Domain.ValueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserMapperTest {

    private UserMapper mapper;
    private CepService cepService;

    @BeforeEach
    void setUp() {
        cepService = Mockito.mock(CepService.class);
        mapper = new UserMapper(cepService);
    }

    @Test
    void deveConverterUserParaDTOReturnUser() {
        Cpf cpf = new Cpf("10917087038");
        User user = new User(
                cpf,
                new Name("João"),
                new Password("aA123456@"),
                new EmailVO("teste@email.com"),
                null,
                new PhoneNumber("11999999999"),
                Role.COMUM
        );

        DTOReturnUser dto = mapper.toDTOReturnUser(user);

        assertNotNull(dto);
        assertEquals(cpf, dto.cpf());
        assertEquals("João", dto.name().name());
    }

    @Test
    void deveCriarUserComEnderecoVindoDoServicoDeCep() {
        DTOReturnCepService mockCepResponse = new DTOReturnCepService(
                "Rua Teste", "Bairro X", "Cidade Y", "SP"
        );
        when(cepService.getAddressByCep(new Cep("01001000"))).thenReturn(mockCepResponse);

        DTOSaveAddress dtoAddress = new DTOSaveAddress(new Cep("01001000"), new ValidText("12A"), "100");
        DTOSaveUser dtoSave = new DTOSaveUser(
                new Cpf("10917087038"),
                new Name("João"),
                new Password("aA123456@"),
                new EmailVO("arthur@email.com"),
                dtoAddress,
                new PhoneNumber("11988887777")
        );

        User user = mapper.registerUser(dtoSave);

        assertNotNull(user);
        assertEquals(Role.COMUM, user.getRole());
        assertEquals("Rua Teste", user.getAddress().rua());
        assertEquals("Cidade Y", user.getAddress().cidade());
        assertEquals(new ValidText("12A"), user.getAddress().numero());
    }

    @Test
    void deveRegistrarAdminCorretamente() {
        DTOReturnCepService mockCepResponse = new DTOReturnCepService(
                "Av Paulista", "Bela Vista", "São Paulo", "SP"
        );
        when(cepService.getAddressByCep(any())).thenReturn(mockCepResponse);

        DTOSaveUser dtoSave = new DTOSaveUser(
                new Cpf("10917087038"),
                new Name("Admin"),
                new Password("Admin123@"),
                new EmailVO("admin@email.com"),
                new DTOSaveAddress(new Cep("01310100"), new ValidText("45"), "1000"),
                new PhoneNumber("11911112222")
        );

        User admin = mapper.registerAdmin(dtoSave);

        assertNotNull(admin);
        assertEquals(Role.ADMIN, admin.getRole());
        assertEquals("Av Paulista", admin.getAddress().rua());
    }

    @Test
    void deveRegistrarMasterCorretamente() {
        DTOSignInMaster dtoMaster = new DTOSignInMaster(
                new Cpf("10917087038"),
                new Name("Master"),
                new Password("Master123@"),
                new EmailVO("master@email.com"),
                new PhoneNumber("11900000000")
        );

        Master master = mapper.registerMaster(dtoMaster);

        assertNotNull(master);
        assertEquals("Master", master.getName().name());
        assertEquals("master@email.com", master.getEmail().email());
    }

    @Test
    void deveCriarDTOSearchUser() {
        Cpf cpf = new Cpf("10917087038");
        Name name = new Name("Busca");

        DTOSearchUser search = mapper.dtoSearchUser(cpf, name, null, null);

        assertNotNull(search);
        assertEquals(cpf, search.cpf());
        assertEquals(name, search.name());
    }
}