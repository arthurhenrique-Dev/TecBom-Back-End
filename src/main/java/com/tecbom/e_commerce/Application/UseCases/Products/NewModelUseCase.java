package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTONewModel;
import com.tecbom.e_commerce.Application.Ports.Input.Products.NewModelPort;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;

public class NewModelUseCase implements NewModelPort {

    private final ProductRepository repository;

    public NewModelUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void newModel(DTONewModel dtoNewModel) {
        Product updatingModelProduct = repository.checkProductById(dtoNewModel.id())
                .orElseThrow(() -> new ProductNotFoundException());
        updatingModelProduct.newModel(
                dtoNewModel.model().name(),
                dtoNewModel.model().price(),
                dtoNewModel.model().quantity(),
                dtoNewModel.model().photos(),
                dtoNewModel.model().DiscountPercentage()
        );
        repository.saveProduct(updatingModelProduct);
    }
}
