package Application.Ports.Input.Products;

import Application.DTOs.Products.DTOSpecificModel;

public interface DeleteModelPort {

    void deleteModel(DTOSpecificModel dtoSpecificModel);
}
