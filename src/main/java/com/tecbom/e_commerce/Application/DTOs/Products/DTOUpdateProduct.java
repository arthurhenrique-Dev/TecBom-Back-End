package com.tecbom.e_commerce.Application.DTOs.Products;

import com.tecbom.e_commerce.Domain.ValueObjects.Photos;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;
import com.tecbom.e_commerce.Domain.ValueObjects.Quantity;
import com.tecbom.e_commerce.Domain.ValueObjects.ValidText;

import java.math.BigDecimal;
import java.util.UUID;

public record DTOUpdateProduct(

        UUID id,
        Integer idxModel,
        ValidText name,
        ValidText modelName,
        Price price,
        Quantity quantity,
        Photos photos,
        BigDecimal discountPercentage
) {
}
