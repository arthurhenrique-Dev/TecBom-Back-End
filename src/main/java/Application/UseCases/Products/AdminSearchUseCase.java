package Application.UseCases.Products;

import Application.DTOs.Products.DTOSearchProduct;
import Application.Ports.Input.Products.AdminSearchPort;
import Application.Ports.Output.ProductRepository;
import Domain.Entities.Products.Product;

import java.util.List;

public class AdminSearchUseCase implements AdminSearchPort {

    private final ProductRepository repository;

    public AdminSearchUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> searchProducts(DTOSearchProduct dtoSearchProduct) {
        return repository.adminSearchProducts(dtoSearchProduct);
    }
}
