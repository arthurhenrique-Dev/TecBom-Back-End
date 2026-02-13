package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOUpdateProduct;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;
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

class UpdateProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private UpdateProductUseCase updateProductUseCase;

    private Product product;
    private DTOUpdateProduct dtoUpdateProduct;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new ReferenceObject().smartphoneProduct();
        id = UUID.randomUUID();

        dtoUpdateProduct = mock(DTOUpdateProduct.class);
        when(dtoUpdateProduct.id()).thenReturn(id);
    }

    @Test
    @DisplayName("Deve atualizar um produto com sucesso")
    void shouldUpdateProductWithSuccess() {
        when(repository.checkProductById(id)).thenReturn(Optional.of(product));

        updateProductUseCase.updateProduct(dtoUpdateProduct);

        verify(repository, times(1)).checkProductById(id);
        verify(repository, times(1)).saveProduct(product);
    }

    @Test
    @DisplayName("Deve lançar ProductNotFoundException quando produto não existir")
    void shouldThrowExceptionWhenProductNotFound() {
        when(repository.checkProductById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> updateProductUseCase.updateProduct(dtoUpdateProduct));

        verify(repository, times(1)).checkProductById(id);
        verify(repository, times(0)).saveProduct(any());
    }
}