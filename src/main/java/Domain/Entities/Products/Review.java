package Domain.Entities.Products;

import Domain.ValueObjects.ValidText;

public record Review(ValidText message, Rating rating, Model model, ValidText reviewerName) {
}
