package Application.DTOs.Users;

import Domain.ValueObjects.Cpf;
import Domain.ValueObjects.Password;

public record DTOUpdatePasswordUser(

        Cpf cpf,
        String token,
        Password password
) {
}
