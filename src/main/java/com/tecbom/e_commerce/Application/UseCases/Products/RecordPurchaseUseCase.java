package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTORecordPurchase;
import com.tecbom.e_commerce.Application.Ports.Input.Products.RecordPurchasePort;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;

public class RecordPurchaseUseCase implements RecordPurchasePort {

    private final ProductRepository repository;

    public RecordPurchaseUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void RecordPurchase(DTORecordPurchase dtoRecordPurchase) {
        Product product = repository.checkProductById(dtoRecordPurchase.id())
                .orElseThrow(() -> new ProductNotFoundException());
        product.RegisterPurchase(dtoRecordPurchase.idxModel(), dtoRecordPurchase.quantity());
        repository.saveProduct(product);
    }
}
