package Application.Ports.Input.Products;

import Application.DTOs.Products.DTOReturnNormalProduct;
import Application.DTOs.Products.DTOSearchProduct;

import java.util.List;

public interface NormalSearchPort {

    List<DTOReturnNormalProduct> searchProducts(DTOSearchProduct dtoSearchProduct);
}
