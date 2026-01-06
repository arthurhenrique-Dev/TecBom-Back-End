package Application.Ports.Input.Products;

import Application.DTOs.Products.DTOSearchProduct;
import Domain.Entities.Products.Product;

import java.util.List;

public interface AdminSearchPort {

    List<Product> searchProducts(DTOSearchProduct dtoSearchProduct);
}
