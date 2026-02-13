package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTORecordPurchase;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Natural;
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

class RecordPurchaseUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private RecordPurchaseUseCase recordPurchaseUseCase;

    private Product product;
    private DTORecordPurchase dtoRecordPurchase;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        id = UUID.randomUUID();
        product = new ReferenceObject().smartphoneProduct();

        Natural quantidade = new Natural(2);

        dtoRecordPurchase = new DTORecordPurchase(id, 1, quantidade.i());
    }

    @Test
    @DisplayName("Deve registrar uma compra com sucesso")
    void shouldRecordPurchaseWithSuccess() {
        when(repository.checkProductById(id)).thenReturn(Optional.of(product));

        recordPurchaseUseCase.RecordPurchase(dtoRecordPurchase);

        verify(repository, times(1)).checkProductById(id);
        verify(repository, times(1)).saveProduct(product);
    }

    @Test
    @DisplayName("Deve lançar ProductNotFoundException quando produto não existir")
    void shouldThrowExceptionWhenProductNotFound() {
        when(repository.checkProductById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> recordPurchaseUseCase.RecordPurchase(dtoRecordPurchase));

        verify(repository, times(1)).checkProductById(id);
        verify(repository, times(0)).saveProduct(any());
    }
}