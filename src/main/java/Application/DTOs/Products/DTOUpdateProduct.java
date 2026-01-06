package Application.DTOs.Products;

import Domain.ValueObjects.Photos;
import Domain.ValueObjects.Price;
import Domain.ValueObjects.Quantity;
import Domain.ValueObjects.ValidText;

import java.math.BigDecimal;
import java.util.List;
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
