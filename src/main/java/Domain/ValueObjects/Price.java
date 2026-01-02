package Domain.ValueObjects;

import Domain.Exceptions.InvalidDataException;

import java.math.BigDecimal;

public class Price {

    private BigDecimal price;

    private BigDecimal validatePrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidDataException("Preço não pode ser menor que zero");
        if (price.scale() != 2) price = price.setScale(2, BigDecimal.ROUND_HALF_UP);
        return price;
    }
}
