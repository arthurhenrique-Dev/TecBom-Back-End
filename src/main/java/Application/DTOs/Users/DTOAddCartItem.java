package Application.DTOs.Users;

import Domain.ValueObjects.CartItem;
import Domain.ValueObjects.Cpf;

public record DTOAddCartItem(

        Cpf cpf,
        CartItem cartItem
) {
}
