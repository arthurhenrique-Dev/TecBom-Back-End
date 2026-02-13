package com.tecbom.e_commerce.Domain.Entities.Products;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.ValueObjects.*;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    private Model model;

    @BeforeEach
    void setUp() {
        model = new ReferenceObject().smartphoneModel();
    }

    @Test
    @DisplayName("Atualizar o modelo com todos os atributos válidos")
    void updateModelWithAllAtributes() {
        ValidText newName = new ValidText("Smartphone X");
        Price newPrice = new Price(new BigDecimal(999.99));
        Quantity newQuantity = new Quantity(50);
        Photos newPhotos = new Photos(List.of("url1.jpg", "url2.jpg"));
        BigDecimal newDiscount = new BigDecimal("10.00");

        model.UpdateModel(newName, newPrice, newQuantity, newPhotos, newDiscount);

        System.out.println(model.getPrice().price());

        assertEquals(newName, model.getName());
        assertEquals(newPrice.discount(newDiscount), model.getPrice());
        assertEquals(newQuantity, model.getQuantity());
        assertEquals(newPhotos, model.getPhotos());
        assertEquals(newDiscount, model.getDiscountPercentage());
    }
    @Test
    @DisplayName("Atualizar o modelo com alguns atributos")
    void updateModelWithSomeAtributes() {
        ValidText newName = new ValidText("Smartphone Y");
        Price newPrice = new Price(new BigDecimal(899.09));
        Quantity oldQuantity = model.getQuantity();
        Photos oldPhotos = model.getPhotos();

        model.UpdateModel(newName, newPrice, null, null, null);

        assertEquals(newName, model.getName());
        assertEquals(newPrice, model.getPrice());
        assertEquals(oldQuantity, model.getQuantity());
        assertEquals(oldPhotos, model.getPhotos());
    }
    @Test
    @DisplayName("Compra de modelo com quantidade válida")
    void purchaseModelWithValidQuantity() {
        Integer quantityPurchased = 5;
        Integer initialQuantity = model.getQuantity().quantity();
        Integer oldTimes = model.getTimesPurchasedInMonth().stats();
        assertEquals(AvailabilityStatus.IN_STOCK, model.getAvailability());
        model.PurchaseModel(quantityPurchased);
        assertEquals(initialQuantity - quantityPurchased, model.getQuantity().quantity());
        assertEquals(model.getTimesPurchasedInMonth().stats(), oldTimes + quantityPurchased);
    }

    @Test
    @DisplayName("Tentativa de comprar um item fora de estoque")
    void purchaseModelOutOfStock() {
        assertEquals(AvailabilityStatus.IN_STOCK, model.getAvailability());
        model.UpdateModel(null, null, new Quantity(0), null, null);
        assertEquals(AvailabilityStatus.OUT_OF_STOCK, model.getAvailability());
        assertThrows(InvalidDataException.class, () -> model.PurchaseModel(1));
    }
    @Test
    @DisplayName("Tentativa de comprar uma quantidade inválida")
    void purchaseModelWithInvalidQuantity() {
        assertEquals(AvailabilityStatus.IN_STOCK, model.getAvailability());
        assertThrows(InvalidDataException.class, () -> model.PurchaseModel(50000000));
    }


    @Test
    void purchaseModel() {
    }

    @Test
    void getTimesPurchased() {
    }

    @Test
    void incrementTimesViewed() {
    }

    @Test
    void getTimesViewedInMonth() {
    }
}