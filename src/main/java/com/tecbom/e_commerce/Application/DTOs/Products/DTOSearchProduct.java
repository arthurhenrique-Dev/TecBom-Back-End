package com.tecbom.e_commerce.Application.DTOs.Products;

import com.tecbom.e_commerce.Domain.Entities.Products.Category;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;

public record DTOSearchProduct(

        String searchTerm,
        Integer idxModel,
        Category category,
        Price price,
        OrderBy orderBy
) {
}
