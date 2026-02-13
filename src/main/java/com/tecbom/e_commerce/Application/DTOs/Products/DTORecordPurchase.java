package com.tecbom.e_commerce.Application.DTOs.Products;

import java.util.UUID;

public record DTORecordPurchase(

        UUID id,
        Integer idxModel,
        Integer quantity
) {
}
