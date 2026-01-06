package Domain.Entities.Products;

import Domain.ValueObjects.*;

import java.math.BigDecimal;
import java.util.List;

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

    public void UpdateModel(ValidText modelName, Price price, Quantity quantity, Photos photos, BigDecimal discountPercentage){
        if (modelName != null || modelName.text().trim().isEmpty()) this.name = modelName;
        if (price != null && price.price().doubleValue() > 0) this.pricePerUnity = price;
        if (quantity != null && quantity.quantity() >= 0){
            this.quantity = quantity;
            this.checkAvailability();
        }
        if (photos != null && !photos.photos().isEmpty()) this.photos = photos;
        if (discountPercentage != null){
            this.pricePerUnity.discount(discountPercentage);
            this.discountPercentage = discountPercentage;
        }
    }

    public Model(ValidText name, Price pricePerUnity, Quantity quantity, Photos photos, BigDecimal discountPercentage) {
        this.name = name;
        this.pricePerUnity = checkDiscount(discountPercentage, pricePerUnity);
        this.quantity = quantity;
        this.photos = photos;
        this.availability = checkAvailability();
        this.timesViewed = 0;
        this.timesPurchased = 0;
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

    public void incrementTimesPurchased() {
        this.timesPurchasedInMonth.repeat();
        this.timesPurchased++;
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
