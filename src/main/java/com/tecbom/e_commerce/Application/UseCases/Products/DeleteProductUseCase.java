package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.Ports.Input.Products.DeleteProductPort;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;

import java.util.List;
import java.util.UUID;

public class DeleteProductUseCase implements DeleteProductPort {

    private final ProductRepository repository;

    public DeleteProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteList(List<UUID> ids) {
        ids.forEach(id -> {
            Product deletingProduct = repository.checkProductById(id)
                    .orElseThrow(() -> new ProductNotFoundException());
            deletingProduct.DeleteProduct();
            repository.saveProduct(deletingProduct);
        });
    }
}
