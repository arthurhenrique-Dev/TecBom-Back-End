package com.tecbom.e_commerce.Application.DTOs.Products;

import java.util.List;
import java.util.UUID;

public record DTOPerProduct(

        UUID productId,
        List<Integer> models
) {
}
