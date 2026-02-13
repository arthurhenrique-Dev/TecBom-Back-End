package com.tecbom.e_commerce.Application.Ports.Input.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTONewModel;

public interface NewModelPort {

    void newModel(DTONewModel dtoNewModel);
}
