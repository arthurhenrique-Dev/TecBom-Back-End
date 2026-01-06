package Application.UseCases.Products;

import Application.DTOs.Products.DTOSaveProduct;
import Application.Mappers.ProductMapper;
import Application.Ports.Input.Products.SaveProductPort;
import Application.Ports.Output.ProductRepository;
import Domain.Entities.Products.Product;

public class SaveProductUseCase implements SaveProductPort {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public SaveProductUseCase(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void saveProductPort(DTOSaveProduct dtoSaveProduct) {
        Product readyToSave = mapper.toRegister(dtoSaveProduct);
        repository.saveProduct(readyToSave);
    }
}
