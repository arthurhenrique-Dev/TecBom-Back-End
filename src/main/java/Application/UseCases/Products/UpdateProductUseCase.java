package Application.UseCases.Products;

import Application.DTOs.Products.DTOUpdateProduct;
import Application.Ports.Input.Products.UpdateProductPort;
import Application.Ports.Output.ProductRepository;
import Domain.Entities.Products.Product;
import Domain.Exceptions.Exceptions.ProductNotFoundException;

public class UpdateProductUseCase implements UpdateProductPort {

    private final ProductRepository repository;

    public UpdateProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void updateProductPort(DTOUpdateProduct dtoUpdateProduct) {
        Product updatingProduct = repository.checkProductById(dtoUpdateProduct.id())
                .orElseThrow(() -> new ProductNotFoundException());
        updatingProduct.UpdateProduct(
                dtoUpdateProduct.idxModel(),
                dtoUpdateProduct.name(),
                dtoUpdateProduct.modelName(),
                dtoUpdateProduct.price(),
                dtoUpdateProduct.quantity(),
                dtoUpdateProduct.photos(),
                dtoUpdateProduct.discountPercentage()
        );
        repository.saveProduct(updatingProduct);
    }
}
