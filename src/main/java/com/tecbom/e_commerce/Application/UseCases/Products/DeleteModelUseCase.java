package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTODeleteModel;
import com.tecbom.e_commerce.Application.Ports.Input.Products.DeleteModelPort;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;

public class DeleteModelUseCase implements DeleteModelPort {

    private final ProductRepository repository;

    public DeleteModelUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteModel(DTODeleteModel dtoSpecific) {
        Product product = repository.checkProductById(dtoSpecific.id())
                .orElseThrow(() -> new ProductNotFoundException());
        product.DeleteModel(dtoSpecific.idxModel());
        repository.saveProduct(product);
    }
}
