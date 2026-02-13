package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOSaveProduct;
import com.tecbom.e_commerce.Application.Mappers.ProductMapper;
import com.tecbom.e_commerce.Application.Ports.Input.Products.SaveProductPort;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;

public class SaveProductUseCase implements SaveProductPort {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public SaveProductUseCase(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void saveProduct(DTOSaveProduct dtoSaveProduct) {
        Product readyToSave = mapper.toRegister(dtoSaveProduct);
        repository.saveProduct(readyToSave);
    }
}
