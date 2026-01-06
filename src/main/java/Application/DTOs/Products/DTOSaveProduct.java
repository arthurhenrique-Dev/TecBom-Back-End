package Application.DTOs.Products;

import Domain.Entities.Products.Category;

import Domain.ValueObjects.ValidText;

import java.util.List;

public record DTOSaveProduct(

        ValidText name,
        ValidText description,
        Category category,
        List<DTOModel>models
) {
}
