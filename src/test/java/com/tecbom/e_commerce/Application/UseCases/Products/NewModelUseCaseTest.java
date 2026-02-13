package com.tecbom.e_commerce.Application.UseCases.Products;

import com.tecbom.e_commerce.Application.DTOs.Products.DTOModel;
import com.tecbom.e_commerce.Application.DTOs.Products.DTONewModel;
import com.tecbom.e_commerce.Application.Ports.Output.ProductRepository;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProductNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Photos;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;
import com.tecbom.e_commerce.Domain.ValueObjects.Quantity;
import com.tecbom.e_commerce.Domain.ValueObjects.ValidText;
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

class NewModelUseCaseTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private NewModelUseCase newModelUseCase;

    private Product product;
    private DTONewModel dtoNewModel;
    private UUID id;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        product = new ReferenceObject().smartphoneProduct();
        id = UUID.randomUUID();

        DTOModel modelData = new DTOModel(
                new ValidText("Novo Modelo Gold"),
                new Price(new BigDecimal("6000.00")),
                new Quantity(20),
                new Photos(List.of("url_gold.jpg")),
                new BigDecimal("5.00")
        );

        dtoNewModel = new DTONewModel(id, modelData);
    }

    @Test
    @DisplayName("Deve adicionar um novo modelo ao produto com sucesso")
    void shouldAddNewModelWithSuccess() {
        when(repository.checkProductById(id)).thenReturn(Optional.of(product));
        int totalModelsBefore = product.getModels().size();

        newModelUseCase.newModel(dtoNewModel);

        verify(repository, times(1)).checkProductById(id);
        verify(repository, times(1)).saveProduct(product);

        assertEquals(totalModelsBefore + 1, product.getModels().size());
        assertEquals("Novo Modelo Gold", product.getModels().get(totalModelsBefore).getName().text());
    }

    @Test
    @DisplayName("Deve lançar ProductNotFoundException quando produto não for encontrado")
    void shouldThrowExceptionWhenProductNotFound() {
        when(repository.checkProductById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> newModelUseCase.newModel(dtoNewModel));

        verify(repository, times(1)).checkProductById(id);
        verify(repository, times(0)).saveProduct(any());
    }
}