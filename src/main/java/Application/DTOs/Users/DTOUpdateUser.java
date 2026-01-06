package Application.DTOs.Users;

import Domain.ValueObjects.Address;
import Domain.ValueObjects.Cpf;
import Domain.ValueObjects.Email;
import Domain.ValueObjects.PhoneNumber;

public record DTOUpdateUser(

        Cpf cpf,
        Email email,
        PhoneNumber phoneNumber,
        Address address
) {
}
