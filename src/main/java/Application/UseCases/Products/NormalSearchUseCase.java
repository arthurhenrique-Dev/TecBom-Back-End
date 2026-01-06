package Application.UseCases.Products;

import Application.DTOs.Products.DTOReturnNormalProduct;
import Application.DTOs.Products.DTOSearchProduct;
import Application.Mappers.ProductMapper;
import Application.Ports.Input.Products.NormalSearchPort;
import Application.Ports.Output.ProductRepository;

import java.util.List;

public class NormalSearchUseCase implements NormalSearchPort {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public NormalSearchUseCase(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<DTOReturnNormalProduct> searchProducts(DTOSearchProduct dtoSearchProduct) {

        return repository.searchProducts(dtoSearchProduct)
                .stream()
                .map(mapper::toReturn)
                .toList();
    }
}
