package com.tecbom.e_commerce.Domain.Entities.Products;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.ValueObjects.*;

import java.math.BigDecimal;

public class Model {

    private ValidText name;
    private Price pricePerUnity;
    private Quantity quantity;
    private Photos photos;
    private AvailabilityStatus availability;
    private Integer timesViewed;
    private Integer timesPurchased;
    private TimesInMonth timesViewedInMonth;
    private TimesInMonth timesPurchasedInMonth;
    private BigDecimal discountPercentage;

    private AvailabilityStatus checkAvailability() {
        return this.quantity.quantity() > 0 ? AvailabilityStatus.IN_STOCK : AvailabilityStatus.OUT_OF_STOCK;
    }

    private Price checkDiscount(BigDecimal discountPercentage, Price pricePerUnity) {
        if (discountPercentage != null) return pricePerUnity.discount(discountPercentage);
        else return pricePerUnity;
    }

    public void UpdateModel(ValidText modelName, Price price, Quantity quantity, Photos photos, BigDecimal discountPercentage) {
        if (modelName != null && !modelName.text().trim().isEmpty()) this.name = modelName;
        if (discountPercentage != null) {
            this.discountPercentage = discountPercentage;
            if (price != null) this.pricePerUnity = checkDiscount(discountPercentage, price);
        } else {
            if (price != null && price.price().doubleValue() > 0) this.pricePerUnity = checkDiscount(this.discountPercentage, price);
        }
        if (quantity != null && quantity.quantity() >= 0) {
            this.quantity = quantity;
            this.availability = checkAvailability();
        }
        if (photos != null && !photos.photos().isEmpty()) this.photos = photos;

    }

    public void PurchaseModel(Integer quantityPurchased) {
        this.availability = checkAvailability();
        if (this.availability == AvailabilityStatus.IN_STOCK) {
            if (quantityPurchased != null && quantityPurchased > 0 && quantityPurchased <= this.quantity.quantity()) {
                for (int i = 0; i < quantityPurchased; i++) {
                    this.timesPurchasedInMonth.repeat();
                }
                this.timesPurchased += quantityPurchased;
                this.quantity = new Quantity(this.quantity.quantity() - quantityPurchased);
                if (this.quantity.quantity() <= 0) this.availability = AvailabilityStatus.OUT_OF_STOCK;
            } else throw new InvalidDataException("quantidade inválida para compra");
        } else throw new InvalidDataException("item fora de estoque");
    }

    public Model(ValidText name, Price pricePerUnity, Quantity quantity, Photos photos, BigDecimal discountPercentage) {
        this.name = name;
        this.pricePerUnity = pricePerUnity.productPrice(checkDiscount(discountPercentage, pricePerUnity));
        this.quantity = quantity;
        this.photos = photos;
        this.availability = checkAvailability();
        this.timesViewed = 0;
        this.timesPurchased = 0;
        this.timesViewedInMonth = new TimesInMonth();
        this.timesPurchasedInMonth = new TimesInMonth();
        this.discountPercentage = discountPercentage;
    }

    public Photos getPhotos() {
        return photos;
    }

    public ValidText getName() {
        return name;
    }

    public Price getPrice() {
        return pricePerUnity;
    }

    public void setPrice(Price pricePerUnity) {
        this.pricePerUnity = pricePerUnity;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public AvailabilityStatus getAvailability() {
        return availability;
    }

    public Integer getTimesViewed() {
        return timesViewed;
    }

    public Integer getTimesPurchased() {
        return timesPurchased;
    }

    public void incrementTimesViewed() {
        this.timesViewedInMonth.repeat();
        this.timesViewed++;
    }

    public TimesInMonth getTimesViewedInMonth() {
        this.timesViewedInMonth.stats();
        return timesViewedInMonth;
    }

    public TimesInMonth getTimesPurchasedInMonth() {
        return timesPurchasedInMonth;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

}