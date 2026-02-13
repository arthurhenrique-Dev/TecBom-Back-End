package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.Ports.Input.Products.NewReviewPort;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Entities.Products.Review;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;

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
