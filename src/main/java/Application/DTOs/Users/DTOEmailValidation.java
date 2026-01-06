package Application.DTOs.Users;

import Domain.ValueObjects.Cpf;

public record DTOEmailValidation(

        Cpf cpf,
        String token
) {
}
