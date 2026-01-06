package Application.DTOs.Users;

import Domain.ValueObjects.Cpf;
import Domain.ValueObjects.Email;
import Domain.ValueObjects.Name;
import Domain.ValueObjects.PhoneNumber;

public record DTOSearchUser(

        Cpf cpf,
        Name name,
        Email email,
        PhoneNumber phoneNumber
) {
}
