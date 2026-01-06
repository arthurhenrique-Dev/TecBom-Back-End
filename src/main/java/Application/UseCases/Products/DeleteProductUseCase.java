package Application.UseCases.Products;

import Application.Ports.Input.Products.DeleteProductPort;
import Application.Ports.Output.ProductRepository;
import Domain.Entities.Products.Product;
import Domain.Exceptions.Exceptions.ProductNotFoundException;

import java.util.List;
import java.util.UUID;

public class DeleteProductUseCase implements DeleteProductPort {

    private final ProductRepository repository;

    public DeleteProductUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void deleteList(List<UUID> ids) {
        ids.forEach(id -> {
            Product deletingProduct = repository.checkProductById(id)
                    .orElseThrow(() -> new ProductNotFoundException());
            deletingProduct.DeleteProduct();
            repository.saveProduct(deletingProduct);
        });
    }
}
