package com.tecbom.e_commerce.Application.UseCases.Products;

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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private DeleteProductUseCase deleteProductUseCase;

    private Product product;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new ReferenceObject().smartphoneProduct();
        id = UUID.randomUUID();
    }

    @Test
    @DisplayName("Deve deletar uma lista de produtos com sucesso")
    void shouldDeleteProductListWithSuccess() {
        when(repository.checkProductById(id)).thenReturn(Optional.of(product));

        deleteProductUseCase.deleteList(List.of(id));

        verify(repository, times(1)).checkProductById(id);
        verify(repository, times(1)).saveProduct(product);
    }

    @Test
    @DisplayName("Deve lançar ProductNotFoundException quando um ID da lista não existir")
    void shouldThrowExceptionWhenAnyIdNotFound() {
        when(repository.checkProductById(id)).thenReturn(Optional.empty());

        List<UUID> ids = List.of(id);

        assertThrows(ProductNotFoundException.class, () -> deleteProductUseCase.deleteList(ids));

        verify(repository, times(1)).checkProductById(id);
        verify(repository, times(0)).saveProduct(any());
    }
}