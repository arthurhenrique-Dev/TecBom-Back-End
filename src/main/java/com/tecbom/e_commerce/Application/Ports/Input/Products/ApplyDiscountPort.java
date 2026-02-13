package com.tecbom.e_commerce.Application.Ports.Input.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTODiscount;

public interface ApplyDiscountPort {

    void applyDiscount(DTODiscount dtoDiscount);
}
