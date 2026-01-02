package Domain.Entities.Products;

import Domain.ValueObjects.Price;
import Domain.ValueObjects.Quantity;
import Domain.ValueObjects.ValidText;

public class Model {

    private ValidText name;
    private Price price;
    private Quantity quantity;
    private AvailabilityStatus availability;
    private Integer timesViewed;
    private Integer timesPurchased;

    private AvailabilityStatus checkAvailability() {
        return this.quantity.quantity() > 0 ? AvailabilityStatus.IN_STOCK : AvailabilityStatus.OUT_OF_STOCK;
    }

    public Model(ValidText name, Price price, Quantity quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.availability = checkAvailability();
        this.timesViewed = 0;
        this.timesPurchased = 0;
    }

    public ValidText getName() {
        return name;
    }

    public void setName(ValidText name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
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
        this.timesViewed++;
    }

    public void incrementTimesPurchased() {
        this.timesPurchased++;
    }
}
