package com.tecbom.e_commerce.Domain.Entities.Products;

import com.tecbom.e_commerce.Domain.ValueObjects.Photos;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;
import com.tecbom.e_commerce.Domain.ValueObjects.Quantity;
import com.tecbom.e_commerce.Domain.ValueObjects.ValidText;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new ReferenceObject().smartphoneProduct();
    }

    @Test
    @DisplayName("Teste de deletação de produto")
    void deleteProduct() {
        assertEquals(Status.ON, product.getStatus());
        product.DeleteProduct();
        assertEquals(Status.OFF, product.getStatus());
    }

    @Test
    @DisplayName("Teste de atualização de produto com todos os parametros")
    void updateProductWithAllAttributes() {
        ValidText newProductName = new ValidText("Notebook Gamer Pro");
        ValidText newModelName = new ValidText("RTX 4090 Edition");
        Price newPrice = new Price(new BigDecimal("9500.00"));
        Quantity newQty = new Quantity(20);
        Photos newPhotos = new Photos(List.of("foto_nova.jpg"));
        BigDecimal newDiscount = new BigDecimal("15.00");

        product.UpdateProduct(1, newProductName, newModelName, newPrice, newQty, newPhotos, newDiscount);

        assertEquals(newProductName, product.getName());
        Model updatedModel = product.getModels().get(0);

        assertEquals(newModelName, updatedModel.getName());
        assertEquals(newPrice.discount(newDiscount), updatedModel.getPrice());
        assertEquals(newQty.quantity(), updatedModel.getQuantity().quantity());
        assertEquals(newDiscount, updatedModel.getDiscountPercentage());
    }

    @Test
    @DisplayName("Teste de atualização de produto com alguns parametros")
    void updateProductWithSomeAttributes() {
        ValidText newProductName = new ValidText("Notebook Gamer Pro");
        Price newPrice = new Price(new BigDecimal("9500.00"));

        product.UpdateProduct(1, newProductName, null, newPrice, null, null, null);

        assertEquals(newProductName, product.getName());
        Model updatedModel = product.getModels().get(0);

        assertEquals(newPrice.discount(updatedModel.getDiscountPercentage()), updatedModel.getPrice());
    }

    @Test
    @DisplayName("Teste de inclusão de modelo")
    void newModel() {
        ValidText extraModelName = new ValidText("Azul Titânio");
        Price extraPrice = new Price(new BigDecimal("8500.00"));
        Quantity extraQty = new Quantity(20);
        Photos extraPhotos = new Photos(List.of("url_azul.jpg"));
        BigDecimal extraDiscount = new BigDecimal("5.0");

        var productModelsSizeBefore = product.getModels().size();
        product.newModel(extraModelName, extraPrice, extraQty, extraPhotos, extraDiscount);
        assertEquals(productModelsSizeBefore + 1, product.getModels().size());
    }

    @Test
    @DisplayName("Teste de exclusão de modelo")
    void deleteModel() {
        ValidText extraModelName = new ValidText("Rosa");
        Price extraPrice = new Price(new BigDecimal("5000.00"));
        Quantity extraQty = new Quantity(20);
        Photos extraPhotos = new Photos(List.of("url_rosa.jpg"));
        BigDecimal extraDiscount = new BigDecimal("2.1");
        product.newModel(extraModelName, extraPrice, extraQty, extraPhotos, extraDiscount);

        var productModelsSizeBefore = product.getModels().size();

        Integer idxModelToDelete = 1;

        product.DeleteModel(idxModelToDelete);

        assertEquals(productModelsSizeBefore - 1, product.getModels().size());
    }

    @Test
    @DisplayName("Teste de registro de compra")
    void registerPurchase() {

        Integer idxModelToPurchase = 1;
        Integer quantityToPurchase = 3;

        Model modelBeforePurchase = product.getModels().get(idxModelToPurchase - 1);
        Integer quantityBefore = modelBeforePurchase.getQuantity().quantity();
        Integer timesPurchasedBefore = modelBeforePurchase.getTimesPurchased();

        product.RegisterPurchase(idxModelToPurchase, quantityToPurchase);

        Model modelAfterPurchase = product.getModels().get(idxModelToPurchase - 1);
        Integer quantityAfter = modelAfterPurchase.getQuantity().quantity();
        Integer timesPurchasedAfter = modelAfterPurchase.getTimesPurchased();

        assertEquals(quantityBefore - quantityToPurchase, quantityAfter);
        assertEquals(timesPurchasedBefore + quantityToPurchase, timesPurchasedAfter);
    }
}