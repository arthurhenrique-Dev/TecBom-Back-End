package com.tecbom.e_commerce.Application.UseCases.Products;

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

class AdminSearchUseCaseTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private AdminSearchUseCase adminSearchUseCase;

    private Product product;
    private DTOSearchProduct dtoSearchProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new ReferenceObject().smartphoneProduct();

        dtoSearchProduct = new DTOSearchProduct(
                "iPhone",
                0,
                Category.TELEFONES,
                new Price(new BigDecimal("5000.00")),
                OrderBy.PRICE_ASC
        );
    }

    @Test
    @DisplayName("Deve buscar produtos como admin com filtros e paginação")
    void shouldSearchProductsWithFilters() {
        // GIVEN
        String searchTerm = "iPhone";
        Integer idxModel = 0;
        Category category = Category.TELEFONES;
        Price price = new Price(new BigDecimal("5000.00"));
        OrderBy orderBy = OrderBy.PRICE_DESC;
        Natural page = new Natural(0);
        Natural size = new Natural(10);

        when(mapper.dtoSearchProduct(searchTerm, idxModel, category, price, orderBy))
                .thenReturn(dtoSearchProduct);

        when(repository.adminSearchProducts(dtoSearchProduct, page, size))
                .thenReturn(List.of(product));

        List<Product> result = adminSearchUseCase.searchProducts(
                searchTerm, idxModel, category, price, orderBy, page, size
        );

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product.getName(), result.get(0).getName());

        verify(mapper, times(1)).dtoSearchProduct(any(), any(), any(), any(), any());
        verify(repository, times(1)).adminSearchProducts(dtoSearchProduct, page, size);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando nenhum produto for encontrado")
    void shouldReturnEmptyListWhenNoProductsFound() {
        Natural page = new Natural(0);
        Natural size = new Natural(10);

        when(mapper.dtoSearchProduct(any(), any(), any(), any(), any())).thenReturn(dtoSearchProduct);
        when(repository.adminSearchProducts(any(), any(), any())).thenReturn(List.of());

        List<Product> result = adminSearchUseCase.searchProducts(
                null, null, null, null, null, page, size
        );

        assertTrue(result.isEmpty());
        verify(repository, times(1)).adminSearchProducts(any(), any(), any());
    }
}