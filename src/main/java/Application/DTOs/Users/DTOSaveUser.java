package Application.DTOs.Users;

import Domain.ValueObjects.*;

public record DTOSaveUser(

        Cpf cpf,
        Name name,
        Password password,
        Email email,
        Address address,
        PhoneNumber phoneNumber
) {
}
