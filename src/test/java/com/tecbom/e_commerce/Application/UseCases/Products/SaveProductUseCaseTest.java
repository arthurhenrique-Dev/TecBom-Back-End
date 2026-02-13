package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOSaveProduct;
import com.tecbom.e_commerce.Application.Mappers.ProductMapper;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class SaveProductUseCaseTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private SaveProductUseCase saveProductUseCase;

    private Product product;
    private DTOSaveProduct dtoSaveProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new ReferenceObject().smartphoneProduct();
        dtoSaveProduct = mock(DTOSaveProduct.class);
    }

    @Test
    @DisplayName("Deve salvar um novo produto com sucesso")
    void shouldSaveProductWithSuccess() {
        when(mapper.toRegister(dtoSaveProduct)).thenReturn(product);

        saveProductUseCase.saveProduct(dtoSaveProduct);

        verify(mapper, times(1)).toRegister(dtoSaveProduct);
        verify(repository, times(1)).saveProduct(product);
    }
}