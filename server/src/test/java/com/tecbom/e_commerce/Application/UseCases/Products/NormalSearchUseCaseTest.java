package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOReturnNormalProduct;
import com.tecbom.e_commerce.Application.DTOs.Products.DTOSearchProduct;
import com.tecbom.e_commerce.Application.DTOs.Products.OrderBy;
import com.tecbom.e_commerce.Application.Mappers.ProductMapper;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Category;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.ValueObjects.Natural;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NormalSearchUseCaseTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private NormalSearchUseCase normalSearchUseCase;

    private Product product;
    private DTOSearchProduct dtoSearchProduct;
    private DTOReturnNormalProduct dtoReturnNormalProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new ReferenceObject().smartphoneProduct();

        dtoSearchProduct = new DTOSearchProduct(
                "Smartphone",
                0,
                Category.TELEFONES,
                new Price(new BigDecimal("1000.00")),
                OrderBy.POPULARITY
        );

        dtoReturnNormalProduct = mock(DTOReturnNormalProduct.class);
    }

    @Test
    @DisplayName("Deve buscar produtos e retornar lista de DTOs com sucesso")
    void shouldSearchProductsAndReturnDtoList() {
        Price price = new Price(new BigDecimal("1000.00"));
        Natural page = new Natural(0);
        Natural size = new Natural(10);

        when(mapper.dtoSearchProduct(anyString(), anyInt(), any(), any(), any())).thenReturn(dtoSearchProduct);
        when(repository.searchProducts(dtoSearchProduct, page, size)).thenReturn(List.of(product));
        when(mapper.toReturn(product)).thenReturn(dtoReturnNormalProduct);

        List<DTOReturnNormalProduct> result = normalSearchUseCase.searchProducts(
                "Smartphone", 0, Category.TELEFONES, price, OrderBy.POPULARITY, page, size
        );

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(dtoReturnNormalProduct, result.get(0));

        verify(mapper, times(1)).dtoSearchProduct(anyString(), anyInt(), any(), any(), any());
        verify(repository, times(1)).searchProducts(dtoSearchProduct, page, size);
        verify(mapper, times(1)).toReturn(product);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nenhum produto for encontrado")
    void shouldReturnEmptyListWhenNoProductsFound() {
        Natural page = new Natural(0);
        Natural size = new Natural(10);

        when(mapper.dtoSearchProduct(any(), any(), any(), any(), any())).thenReturn(dtoSearchProduct);
        when(repository.searchProducts(any(), any(), any())).thenReturn(List.of());

        List<DTOReturnNormalProduct> result = normalSearchUseCase.searchProducts(
                null, null, null, null, null, page, size
        );

        assertTrue(result.isEmpty());
        verify(repository, times(1)).searchProducts(any(), any(), any());
        verify(mapper, never()).toReturn(any());
    }
}