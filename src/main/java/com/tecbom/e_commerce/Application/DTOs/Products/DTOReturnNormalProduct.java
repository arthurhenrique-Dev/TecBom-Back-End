package com.tecbom.e_commerce.Application.DTOs.Products;

import com.tecbom.e_commerce.Domain.Entities.Products.Category;
import com.tecbom.e_commerce.Domain.Entities.Products.Model;
import com.tecbom.e_commerce.Domain.ValueObjects.ValidText;

import java.util.List;
import java.util.UUID;

public record DTOReturnNormalProduct(

        UUID id,
        ValidText name,
        ValidText description,
        Category category,
        List<Model> model
) {
}
