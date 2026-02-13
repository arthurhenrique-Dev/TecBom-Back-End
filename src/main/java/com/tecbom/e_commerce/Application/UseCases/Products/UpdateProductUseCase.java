package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOUpdateProduct;
import com.tecbom.e_commerce.Application.Ports.Input.Products.UpdateProductPort;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;

public class UpdateProductUseCase implements UpdateProductPort {

    private final ProductRepository repository;

    public UpdateProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateProduct(DTOUpdateProduct dtoUpdateProduct) {
        Product updatingProduct = repository.checkProductById(dtoUpdateProduct.id())
                .orElseThrow(() -> new ProductNotFoundException());
        updatingProduct.UpdateProduct(
                dtoUpdateProduct.idxModel(),
                dtoUpdateProduct.name(),
                dtoUpdateProduct.modelName(),
                dtoUpdateProduct.price(),
                dtoUpdateProduct.quantity(),
                dtoUpdateProduct.photos(),
                dtoUpdateProduct.discountPercentage()
        );
        repository.saveProduct(updatingProduct);
    }
}
