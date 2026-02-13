package com.tecbom.e_commerce.Application.Ports.Input.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOAddCartItem;

public interface AddCartItemPort {

    void addCartItem(DTOAddCartItem dtoAddCartItem);
}
