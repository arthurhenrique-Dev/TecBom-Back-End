package Application.UseCases.Products;

import Application.Ports.Input.Products.NewReviewPort;
import Application.Ports.Output.ProductRepository;
import Domain.Entities.Products.Product;
import Domain.Entities.Products.Review;
import Domain.Exceptions.Exceptions.ProductNotFoundException;

public class NewReviewUseCase implements NewReviewPort {

    private final ProductRepository repository;

    public NewReviewUseCase(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public void newReview(Review review) {
        Product produto = repository.checkProductById(review.id())
                .orElseThrow(() -> new ProductNotFoundException());
        produto.AddReview(review);
        repository.saveProduct(produto);
    }
}
