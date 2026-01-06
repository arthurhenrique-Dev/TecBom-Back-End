package Application.DTOs.Products;

import Domain.ValueObjects.Photos;
import Domain.ValueObjects.Price;
import Domain.ValueObjects.Quantity;
import Domain.ValueObjects.ValidText;

import java.math.BigDecimal;

public record DTOModel(

        ValidText name,
        Price price,
        Quantity quantity,
        Photos photos,
        BigDecimal DiscountPercentage
) {
}
