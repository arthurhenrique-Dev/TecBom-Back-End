package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTORemoveCartItem;

public interface RemoveCartItemPort {

    void removeCartItem(DTORemoveCartItem dtoRemoveCartItem);
}
