package com.tecbom.e_commerce.Infra.Adapters.Output.Persistence.NoSQL.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "carts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartEntity {

    @Id
    private String cpf;
    private List<CartItemEntity> items;
    private Double totalPrice;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItemEntity {
        private Integer id;
        private String name;
        private Integer quantity;
        private Double price;
    }
}
