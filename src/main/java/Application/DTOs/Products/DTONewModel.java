package Application.DTOs.Products;

import Domain.Entities.Products.Model;

import java.util.UUID;

public record DTONewModel(

        UUID id,
        DTOModel model
) {
}
