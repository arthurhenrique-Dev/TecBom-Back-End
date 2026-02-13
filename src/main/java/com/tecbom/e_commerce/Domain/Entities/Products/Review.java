package com.tecbom.e_commerce.Domain.Entities.Products;

import com.tecbom.e_commerce.Domain.ValueObjects.ValidText;

import java.util.UUID;

public record Review(ValidText message, Rating rating, UUID id, Integer idxModel, ValidText reviewerName) {
}
