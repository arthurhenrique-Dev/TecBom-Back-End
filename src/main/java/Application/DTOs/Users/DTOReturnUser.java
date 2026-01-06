package Application.DTOs.Users;

import Domain.Entities.Users.Cart;
import Domain.ValueObjects.*;

public record DTOReturnUser(

    Cpf cpf,
    Name name,
    Email email,
    PhoneNumber phoneNumber,
    Address address,
    Cart cart
) {
}
