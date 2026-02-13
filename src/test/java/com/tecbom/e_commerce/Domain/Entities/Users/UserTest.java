package com.tecbom.e_commerce.Domain.Entities.Users;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;
import com.tecbom.e_commerce.Domain.ValueObjects.*;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private User userRegister;
    @BeforeEach
    void setUp() {
        user = new ReferenceObject().NormalUser();
        userRegister = new ReferenceObject().UserRegister();
    }

    @Test
    @DisplayName("Deve ativar usuário ao validar e-mail com token correto")
    void validateEmailWithValidToken() {
        String validToken = userRegister.getEmailValidation().token();
        userRegister.ValidateEmail(validToken);
        assertTrue(userRegister.getEmailValidation().validated());
        assertEquals(Status.ON, userRegister.getStatus());
    }

    @Test
    @DisplayName("Não deve ativar usuário ao validar e-mail com token errado")
    void invalidateEmailWithValidToken() {
        String invalidToken = "invalid-token";
        assertThrows(ValidationFailedException.class, () -> userRegister.ValidateEmail(invalidToken));
        assertEquals(Status.VALIDATING, userRegister.getStatus());
    }

    @Test
    @DisplayName(("Deve iniciar processo de alteração de senha"))
    void startChangePasswordWithValidatedEmail() {
        user.StartChangePassword();
        assertNotNull(user.getPasswordUpdater());
    }

    @Test
    @DisplayName(("Não deve iniciar processo de alteração de senha"))
    void startChangePasswordWithInvalidEmail() {
        assertThrows(ValidationFailedException.class, () -> userRegister.StartChangePassword());
        assertNull(userRegister.getPasswordUpdater());
    }

    @Test
    @DisplayName("Deve alterar senha com token válido")
    void changePasswordWithValidToken() {
        user.StartChangePassword();
        String validToken = user.getPasswordUpdater().token();
        Password newPassword = new Password("newSecurePassword123@");
        user.ChangePassword(validToken, newPassword);
        assertEquals(newPassword, user.getPassword());
        assertNull(user.getPasswordUpdater());
    }

    @Test
    @DisplayName("Não deve alterar senha com token inválido")
    void changePasswordWithInvalidToken() {
        user.StartChangePassword();
        String validToken = "token-invalido-nada-a-ver";
        Password newPassword = new Password("newSecurePassword123@");
        assertThrows(ValidationFailedException.class, () -> user.ChangePassword(validToken, newPassword));
        assertNotEquals(newPassword, user.getPassword());
        assertNotNull(user.getPasswordUpdater());
    }

    @Test
    @DisplayName("Deve desativar usuário")
    void validDeactivate() {
        assertEquals(Status.ON, user.getStatus());
        user.Deactivate();
        assertEquals(Status.OFF, user.getStatus());
    }

    @Test
    @DisplayName("Não deve desativar usuário já desativado")
    void invalidDeactivate() {
        assertEquals(Status.ON, user.getStatus());
        user.Deactivate();
        assertEquals(Status.OFF, user.getStatus());
        assertThrows(InvalidDataException.class, () -> user.Deactivate());
    }

    @Test
    @DisplayName("Deve reativar usuário")
    void validReactivate() {
        assertEquals(Status.ON, user.getStatus());
        user.Deactivate();
        assertEquals(Status.OFF, user.getStatus());
        user.Reactivate();
        assertEquals(Status.ON, user.getStatus());
    }

    @Test
    @DisplayName("Não deve reativar usuário já ativo")
    void invalidReactivate() {
        assertEquals(Status.ON, user.getStatus());
        assertThrows(InvalidDataException.class, () -> user.Reactivate());
    }

    @Test
    @DisplayName("Deve ser feita a atualização geral de usuário")
    void updateUserWithAllAttributes() {
        EmailVO newEmail = new EmailVO("emailNovo@gmail.com");
        PhoneNumber newPhone = new PhoneNumber("11987654322");
        Cep newCep = new Cep("04535003");
        ValidText number = new ValidText("84");
        Address address = new Address(
                newCep,
                "Rua Exemplo",
                "Bairro Exemplo",
                "Cidade Exemplo",
                "Estado Exemplo",
                "apto 83",
                number
        );
        assertNotEquals(newEmail, user.getEmail());
        assertNotEquals(newPhone, user.getPhoneNumber());
        assertNotEquals(address, user.getAddress());
        user.UpdateUser(newEmail, newPhone, address);
        assertEquals(newEmail, user.getEmail());
        assertEquals(newPhone, user.getPhoneNumber());
        assertEquals(address, user.getAddress());
    }

    @Test
    @DisplayName("Deve ser feita a atualização parcial de usuário")
    void updateUserWithSomeAttributes() {
        EmailVO newEmail = new EmailVO("emailNovo@gmail.com");
        PhoneNumber newPhone = new PhoneNumber("11987654322");
        Address oldAddress = user.getAddress();
        assertNotEquals(newEmail, user.getEmail());
        assertNotEquals(newPhone, user.getPhoneNumber());
        user.UpdateUser(newEmail, newPhone, null);
        assertEquals(newEmail, user.getEmail());
        assertEquals(newPhone, user.getPhoneNumber());
        assertEquals(oldAddress, user.getAddress());
    }

    @Test
    @DisplayName("Deve ser feita a promoção de usuário comum para admin")
    void hireComumToAdmin() {
        assertEquals(Role.COMUM, user.getRole());
        user.HireUser();
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    @DisplayName("Não deve ser feita a promoção de usuário já admin")
    void hireAnAdmin() {
        user.HireUser();
        assertEquals(Role.ADMIN, user.getRole());
        assertThrows(InvalidDataException.class, () -> user.HireUser());
    }

    @Test
    @DisplayName("Deve ser feita a demissão de um admin para comum")
    void dismissAdmin() {
        user.HireUser();
        assertEquals(Role.ADMIN, user.getRole());
        user.DismissAdmin();
        assertEquals(Role.COMUM, user.getRole());
    }

    @Test
    @DisplayName("Não deve ser feita a demissão de um usuário que não é admin")
    void dismissAComum() {
        assertEquals(Role.COMUM, user.getRole());
        assertThrows(InvalidDataException.class, () -> user.DismissAdmin());
    }
}