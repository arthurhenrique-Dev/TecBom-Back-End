package com.tecbom.e_commerce.Application.DTOs.Products;

import java.math.BigDecimal;
import java.util.List;

public record DTODiscount(

    List<DTOPerProduct> dtoPerProduct,
    BigDecimal discountPercentage
) {
}
