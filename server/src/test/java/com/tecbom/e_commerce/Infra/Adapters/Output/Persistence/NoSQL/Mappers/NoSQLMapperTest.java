package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Mappers;

import com.tecbom.e_commerce.Domain.Entities.Products.*;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.ValueObjects.*;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Models.CartEntity;
import com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Models.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NoSQLMapperTest {

    private NoSQLMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new NoSQLMapper();
    }

    @Test
    void deveConverterProductParaEntity() {
        Model model = new Model(
                new ValidText("Modelo A"),
                new Price(new BigDecimal("100.00")),
                new Quantity(10),
                new Photos(List.of("url1")),
                new BigDecimal("10.00")
        );

        Review review = new Review(
                new ValidText("Bom"),
                Rating.FIVE_STARS,
                UUID.randomUUID(),
                0,
                new ValidText("Arthur")
        );

        Product product = new Product(
                UUID.randomUUID(),
                new ValidText("Produto Teste"),
                new ValidText("Descricao"),
                Category.TELEFONES,
                List.of(model),
                List.of(review),
                new Quantity(10),
                AvailabilityStatus.IN_STOCK,
                5, 2, null, null,
                LocalDateTime.now(),
                Status.ON
        );

        ProductEntity entity = mapper.toEntity(product);

        assertNotNull(entity);
        assertEquals(90.0, entity.getModels().get(0).getPrice(), 0.001);
    }

    @Test
    void deveConverterEntityParaProductDomain() {
        ProductEntity.ModelEntity modelEntity = new ProductEntity.ModelEntity(
                "Modelo A", 100.0, 10, List.of("url1"), "AVAILABLE", 5, 2, 10.0
        );

        ProductEntity.ReviewEntity reviewEntity = new ProductEntity.ReviewEntity(
                UUID.randomUUID(), "Bom", 4, 0, "Arthur"
        );

        UUID productId = UUID.randomUUID();
        ProductEntity entity = new ProductEntity(
                productId, "Produto Teste", "Descricao", Category.TELEFONES,
                List.of(modelEntity), List.of(reviewEntity),
                10, AvailabilityStatus.IN_STOCK, 5, 2, null, null,
                LocalDateTime.now(), Status.ON
        );

        Product product = mapper.toDomain(entity);

        assertNotNull(product);
        assertEquals(90.0, product.getModels().get(0).getPrice().price().doubleValue(), 0.001);
    }

    @Test
    void deveConverterCartParaEntity() {
        Cpf cpf = new Cpf("10917087038");
        CartItem item = new CartItem(
                1,
                new ValidText("Mouse Gamer"),
                new Quantity(1),
                new Price(new BigDecimal("150.00"))
        );

        Cart cart = new Cart(cpf, List.of(item));
        CartEntity entity = mapper.toEntity(cart);

        assertNotNull(entity);
        assertEquals(150.0, entity.getItems().get(0).getPrice(), 0.001);
    }

    @Test
    void deveConverterEntityParaCartDomain() {
        CartEntity.CartItemEntity itemEntity = new CartEntity.CartItemEntity(
                1, "Mouse Gamer", 1, 150.0
        );
        String cpfRaw = "10917087038";
        CartEntity entity = new CartEntity(cpfRaw, List.of(itemEntity), 150.0);

        Cart cart = mapper.toDomain(entity);

        assertNotNull(cart);
        assertEquals(cpfRaw, cart.getCpf().cpf());
    }

    @Test
    void deveRetornarNullParaEntradasNulas() {
        assertNull(mapper.toDomain((ProductEntity) null));
        assertNull(mapper.toDomain((CartEntity) null));
        assertNull(mapper.toEntity((Cart) null));
    }
}