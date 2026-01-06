package Application.UseCases.Products;

import Application.DTOs.Products.DTODiscount;
import Application.Ports.Input.Products.ApplyDiscountPort;
import Application.Ports.Output.ProductRepository;
import Domain.Entities.Products.Product;
import Domain.Exceptions.Exceptions.ProductNotFoundException;

public class ApplyDiscountUseCase implements ApplyDiscountPort {

    private final ProductRepository repository;

    public ApplyDiscountUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void applyDiscountPort(DTODiscount dtoDiscount) {
        dtoDiscount.dtoPerProduct().forEach(dtoPerProduct -> {
            Product product = repository.checkProductById(dtoPerProduct.productId())
                    .orElseThrow(() -> new ProductNotFoundException());
            dtoPerProduct.models().forEach(dtoPerProductModel -> {
                product.getModels().get(dtoPerProductModel - 1).getPrice().discount(dtoDiscount.discountPercentage());
            });
            repository.saveProduct(product);
        });
    }
}
