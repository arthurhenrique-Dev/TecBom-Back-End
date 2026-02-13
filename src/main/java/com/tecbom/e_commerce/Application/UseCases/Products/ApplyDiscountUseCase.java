package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTODiscount;
import com.tecbom.e_commerce.Application.Ports.Input.Products.ApplyDiscountPort;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;

public class ApplyDiscountUseCase implements ApplyDiscountPort {

    private final ProductRepository repository;

    public ApplyDiscountUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void applyDiscount(DTODiscount dtoDiscount) {
        dtoDiscount.dtoPerProduct().forEach(dtoPerProduct -> {
            Product product = repository.checkProductById(dtoPerProduct.productId())
                    .orElseThrow(() -> new ProductNotFoundException());
            dtoPerProduct.models().forEach(dtoPerProductModel -> {
                product.getModels().get(dtoPerProductModel - 1).getPrice().discount(dtoDiscount.discountPercentage());
            });
            repository.saveProduct(product);
        });
    }
}
