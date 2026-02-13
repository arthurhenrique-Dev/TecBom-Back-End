package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTODeleteModel;
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

class DeleteModelUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private DeleteModelUseCase deleteModelUseCase;

    private Product product;
    private DTODeleteModel dtoDeleteModel;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new ReferenceObject().smartphoneProduct();
        id = UUID.randomUUID();
        dtoDeleteModel = new DTODeleteModel(id, 1);
    }

    @Test
    @DisplayName("Deve deletar um modelo de produto com sucesso")
    void shouldDeleteModelWithSuccess() {
        when(repository.checkProductById(dtoDeleteModel.id())).thenReturn(Optional.of(product));
        int totalModelsBefore = product.getModels().size();

        deleteModelUseCase.deleteModel(dtoDeleteModel);

        verify(repository, times(1)).checkProductById(dtoDeleteModel.id());
        verify(repository, times(1)).saveProduct(product);
        assertEquals(totalModelsBefore - 1, product.getModels().size());
    }

    @Test
    @DisplayName("Deve lançar ProductNotFoundException quando produto não existir")
    void shouldThrowExceptionWhenProductNotFound() {
        when(repository.checkProductById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> deleteModelUseCase.deleteModel(dtoDeleteModel));

        verify(repository, times(1)).checkProductById(id);
        verify(repository, times(0)).saveProduct(any());
    }
}