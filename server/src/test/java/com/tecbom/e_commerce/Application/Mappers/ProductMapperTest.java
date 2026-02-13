package com.tecbom.e_commerce.Application.Mappers;

import com.tecbom.e_commerce.Application.DTOs.Products.*;
import com.tecbom.e_commerce.Domain.Entities.Products.Category;
import com.tecbom.e_commerce.Domain.Entities.Products.Model;
import com.tecbom.e_commerce.Domain.Entities.Products.Product;
import com.tecbom.e_commerce.Domain.ValueObjects.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ProductMapper();
    }

    @Test
    void deveConverterDTOSaveParaProductDomain() {
        DTOModel dtoModel = new DTOModel(
                new ValidText("Modelo X"),
                new Price(new BigDecimal("100.00")),
                new Quantity(5),
                new Photos(List.of("url")),
                new BigDecimal("10.00")
        );

        DTOSaveProduct dtoSave = new DTOSaveProduct(
                new ValidText("Produto"),
                new ValidText("Descricao"),
                Category.TELEFONES,
                List.of(dtoModel)
        );

        Product product = mapper.toRegister(dtoSave);

        assertNotNull(product);
        assertEquals("Produto", product.getName().text());
        assertEquals(1, product.getModels().size());
        assertEquals("Modelo X", product.getModels().get(0).getName().text());
        // Aqui valida se o desconto foi aplicado na construção (100 - 10% = 90)
        assertEquals(90.0, product.getModels().get(0).getPrice().price().doubleValue(), 0.001);
    }

    @Test
    void deveConverterProductParaDTOReturn() {
        Model model = new Model(
                new ValidText("Modelo A"),
                new Price(new BigDecimal("200.00")),
                new Quantity(10),
                new Photos(List.of("url")),
                BigDecimal.ZERO
        );

        Product product = new Product(
                new ValidText("Smartphone"),
                new ValidText("Top de linha"),
                List.of(model),
                Category.TELEFONES
        );

        DTOReturnNormalProduct dtoReturn = mapper.toReturn(product);

        assertNotNull(dtoReturn);
        assertEquals(product.getId(), dtoReturn.id());
        assertEquals(product.getName(), dtoReturn.name());
        assertEquals(1, dtoReturn.model().size());
    }

    @Test
    void deveCriarDTOSearchProductCorretamente() {
        Price priceLimit = new Price(new BigDecimal("500.00"));

        DTOSearchProduct search = mapper.dtoSearchProduct(
                "iphone", 0, Category.TELEFONES, priceLimit, OrderBy.PRICE_ASC
        );

        assertNotNull(search);
        assertEquals("iphone", search.searchTerm());
        assertEquals(Category.TELEFONES, search.category());
        assertEquals(priceLimit, search.price());
    }
}