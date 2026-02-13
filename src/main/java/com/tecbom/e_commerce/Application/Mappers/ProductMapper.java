package com.tecbom.e_commerce.Application.Mappers;

import com.tecbom.e_commerce.Application.DTOs.Products.*;
import com.tecbom.e_commerce.Application.DTOs.Users.DTOSearchUser;
import com.tecbom.e_commerce.Domain.Entities.Products.Category;
import com.tecbom.e_commerce.Domain.Entities.Products.Model;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;

public class ProductMapper {

    Model modelCreator(DTOModel dtoModel) {
        Model model = new Model(
                dtoModel.name(),
                dtoModel.price(),
                dtoModel.quantity(),
                dtoModel.photos(),
                dtoModel.DiscountPercentage()
        );
        return model;
    }

    public Product toRegister(DTOSaveProduct dtoSaveProduct) {

        Product product = new Product(
                dtoSaveProduct.name(),
                dtoSaveProduct.description(),
                dtoSaveProduct.models().stream().map(this::modelCreator).toList(),
                dtoSaveProduct.category()
        );
        return product;
    }

    public DTOReturnNormalProduct toReturn(Product product) {
        DTOReturnNormalProduct returnProduct = new DTOReturnNormalProduct(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getCategory(),
                product.getModels()
        );
        return returnProduct;
    }
    public DTOSearchProduct dtoSearchProduct(String searchTerm, Integer idxModel, Category category, Price price, OrderBy orderBy){
        return new DTOSearchProduct(
                searchTerm,
                idxModel,
                category,
                price,
                orderBy);
    }
}
