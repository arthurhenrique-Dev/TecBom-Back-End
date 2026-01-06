package Application.Ports.Input.User;

import Application.DTOs.Users.DTORemoveCartItem;

public interface RemoveCartItemPort {

    void removeCartItem(DTORemoveCartItem dtoRemoveCartItem);
}
