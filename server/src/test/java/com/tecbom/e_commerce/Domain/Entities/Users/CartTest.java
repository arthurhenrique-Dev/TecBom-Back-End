package com.tecbom.e_commerce.Domain.Entities.Users;

import com.tecbom.e_commerce.Domain.ValueObjects.CartItem;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {

    private Cart cart;
    private CartItem simpleItem, bulkItem;

    @BeforeEach
    void setUp() {
        cart = new ReferenceObject().fullCart();
        simpleItem = new ReferenceObject().simpleItem();
        bulkItem = new ReferenceObject().bulkItem();
    }

    @Test
    @DisplayName("Deve adicionar um item ao carrinho e atualizar o preço total")
    void addCartItem() {
        Price beforePrice = cart.getPrice();
        cart.addCartItem(simpleItem);
        Price afterPrice = cart.getPrice();
        assertTrue(afterPrice.price().subtract(beforePrice.price()).equals(simpleItem.getPrice().price()));
        assertTrue(cart.getItems().contains(simpleItem));
    }

    @Test
    @DisplayName("Deve remover um item do carrinho e atualizar o preço total")
    void removeCartItem() {
        cart.addCartItem(bulkItem);
        Price beforePrice = cart.getPrice();
        cart.removeCartItem(1);
        Price afterPrice = cart.getPrice();
        assertFalse(cart.getItems().contains(bulkItem));
        assertTrue(beforePrice.price().subtract(afterPrice.price()).equals(bulkItem.getPrice().price()));
    }
}