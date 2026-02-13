package com.tecbom.e_commerce.Application.Ports.Output;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOSearchProduct;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.ValueObjects.Natural;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    void saveProduct(Product product);

    List<Product> adminSearchProducts(DTOSearchProduct dtoSearchProduct, Natural pages, Natural size);

    List<Product> searchProducts(DTOSearchProduct dtoSearchProduct, Natural pages, Natural size);

    Optional<Product> checkProductById(UUID id);
}
