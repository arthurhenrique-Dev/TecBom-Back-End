package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOReturnNormalProduct;
import com.tecbom.e_commerce.Application.DTOs.Products.DTOSearchProduct;
import com.tecbom.e_commerce.Application.DTOs.Products.OrderBy;
import com.tecbom.e_commerce.Application.Mappers.ProductMapper;
import com.tecbom.e_commerce.Application.Ports.Input.Products.NormalSearchPort;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Category;
import com.tecbom.e_commerce.Domain.ValueObjects.Natural;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;

import java.util.List;

public class NormalSearchUseCase implements NormalSearchPort {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public NormalSearchUseCase(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<DTOReturnNormalProduct> searchProducts(String searchTerm, Integer idxModel, Category category, Price price, OrderBy orderBy, Natural pages, Natural size) {
        DTOSearchProduct dtoSearchProduct = mapper.dtoSearchProduct(searchTerm, idxModel, category, price, orderBy);
        return repository.searchProducts(dtoSearchProduct, pages, size)
                .stream()
                .map(mapper::toReturn)
                .toList();
    }
}
