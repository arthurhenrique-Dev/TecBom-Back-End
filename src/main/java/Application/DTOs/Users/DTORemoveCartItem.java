package Application.DTOs.Users;

import Domain.ValueObjects.Cpf;

public record DTORemoveCartItem(

        Cpf cpf,
        Integer idxItem
) {
}
