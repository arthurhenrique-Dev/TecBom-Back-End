package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTODiscount;
import com.tecbom.e_commerce.Application.DTOs.Products.DTOPerProduct;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplyDiscountUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ApplyDiscountUseCase applyDiscountUseCase;

    private Product product;
    private DTODiscount dtoDiscount;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new ReferenceObject().smartphoneProduct();

        id = UUID.randomUUID();
        DTOPerProduct dtoPerProduct = new DTOPerProduct(id, List.of(1));
        dtoDiscount = new DTODiscount(List.of(dtoPerProduct), new BigDecimal("10.00"));
    }

    @Test
    @DisplayName("Deve aplicar desconto em modelos específicos de produtos")
    void shouldApplyDiscountToSelectedModels() {
        when(repository.checkProductById(id)).thenReturn(Optional.of(product));

        applyDiscountUseCase.applyDiscount(dtoDiscount);

        verify(repository, times(1)).checkProductById(id);
        verify(repository, times(1)).saveProduct(product);

        assertEquals(new BigDecimal("4500.00"), product.getModels().get(0).getPrice().price());

        assertEquals(new BigDecimal("5000.00"), product.getModels().get(1).getPrice().price());
    }

    @Test
    @DisplayName("Deve lançar ProductNotFoundException quando o produto não existir")
    void shouldThrowExceptionWhenProductNotFound() {
        when(repository.checkProductById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            applyDiscountUseCase.applyDiscount(dtoDiscount);
        });

        verify(repository, times(0)).saveProduct(any());
    }
}