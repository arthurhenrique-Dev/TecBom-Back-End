package com.tecbom.e_commerce.Application.Ports.Input.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOSaveProduct;

public interface SaveProductPort {

    void saveProduct(DTOSaveProduct dtoSaveProduct);
}
