package com.tecbom.e_commerce.Application.Ports.Input.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTODeleteModel;

public interface DeleteModelPort {

    void deleteModel(DTODeleteModel dtoDeleteModel);
}
