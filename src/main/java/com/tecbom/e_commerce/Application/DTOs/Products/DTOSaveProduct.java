package com.tecbom.e_commerce.Application.DTOs.Products;

import com.tecbom.e_commerce.Domain.Entities.Products.Category;

import com.tecbom.e_commerce.Domain.ValueObjects.ValidText;

import java.util.List;

public record DTOSaveProduct(

        ValidText name,
        ValidText description,
        Category category,
        List<DTOModel>models
) {
}
