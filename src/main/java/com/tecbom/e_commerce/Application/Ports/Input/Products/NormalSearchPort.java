package com.tecbom.e_commerce.Application.Ports.Input.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOReturnNormalProduct;
import com.tecbom.e_commerce.Application.DTOs.Products.DTOSearchProduct;
import com.tecbom.e_commerce.Application.DTOs.Products.OrderBy;
import com.tecbom.e_commerce.Domain.Entities.Products.Category;
import com.tecbom.e_commerce.Domain.ValueObjects.Natural;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;

import java.util.List;

public interface NormalSearchPort {

    List<DTOReturnNormalProduct> searchProducts(String searchTerm, Integer idxModel, Category category, Price price, OrderBy orderBy, Natural pages, Natural size);
}
