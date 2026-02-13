package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Entities.Products.Rating;
import com.tecbom.e_commerce.Domain.Entities.Products.Review;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.ValidText;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewReviewUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private NewReviewUseCase newReviewUseCase;

    private Product product;
    private Review review;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new ReferenceObject().smartphoneProduct();
        id = UUID.randomUUID();

        review = new Review(
                new ValidText("Excelente produto, super recomendo!"),
                Rating.FIVE_STARS,
                id,
                1,
                new ValidText("Arthur")
        );
    }

    @Test
    @DisplayName("Deve adicionar uma avaliação ao produto com sucesso")
    void shouldAddReviewWithSuccess() {
        when(repository.checkProductById(review.id())).thenReturn(Optional.of(product));

        newReviewUseCase.newReview(review);

        verify(repository, times(1)).checkProductById(review.id());
        verify(repository, times(1)).saveProduct(product);
        assertTrue(product.getReviews().contains(review));
    }

    @Test
    @DisplayName("Deve lançar ProductNotFoundException quando o produto da review não existir")
    void shouldThrowExceptionWhenProductNotFound() {
        when(repository.checkProductById(review.id())).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> newReviewUseCase.newReview(review));

        verify(repository, times(1)).checkProductById(review.id());
        verify(repository, times(0)).saveProduct(any());
    }
}