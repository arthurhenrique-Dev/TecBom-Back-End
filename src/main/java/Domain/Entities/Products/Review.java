package Domain.Entities.Products;

import Domain.ValueObjects.ValidText;

import java.util.UUID;

public record Review(ValidText message, Rating rating, UUID id, Integer idxModel, ValidText reviewerName) {
}
