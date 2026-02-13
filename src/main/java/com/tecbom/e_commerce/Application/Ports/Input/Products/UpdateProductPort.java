package com.tecbom.e_commerce.Application.Ports.Input.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOUpdateProduct;

public interface UpdateProductPort {

    void updateProduct(DTOUpdateProduct dtoUpdateProduct);
}
