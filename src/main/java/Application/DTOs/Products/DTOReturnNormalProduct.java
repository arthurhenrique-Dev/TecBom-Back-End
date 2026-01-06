package Application.DTOs.Products;

import Domain.Entities.Products.Category;
import Domain.Entities.Products.Model;
import Domain.ValueObjects.ValidText;

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
