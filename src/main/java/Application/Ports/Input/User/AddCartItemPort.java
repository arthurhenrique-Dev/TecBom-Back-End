package Application.Ports.Input.User;

import Application.DTOs.Users.DTOAddCartItem;

public interface AddCartItemPort {

    void addCartItem(DTOAddCartItem dtoAddCartItem);
}
