package Application.UseCases.Products;

import Application.DTOs.Products.DTONewModel;
import Application.Ports.Input.Products.NewModelPort;
import Application.Ports.Output.ProductRepository;
import Domain.Entities.Products.Product;
import Domain.Exceptions.Exceptions.ProductNotFoundException;

public class NewModelUseCase implements NewModelPort {

    private final ProductRepository repository;

    public NewModelUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void newModel(DTONewModel dtoNewModel) {
        Product updatingModelProduct = repository.checkProductById(dtoNewModel.id())
                .orElseThrow(() -> new ProductNotFoundException());
        updatingModelProduct.newModel(
                dtoNewModel.model().name(),
                dtoNewModel.model().price(),
                dtoNewModel.model().quantity(),
                dtoNewModel.model().photos(),
                dtoNewModel.model().DiscountPercentage()
        );
        repository.saveProduct(updatingModelProduct);
    }
}
