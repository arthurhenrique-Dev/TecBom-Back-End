package Domain.ValueObjects;

public record Quantity(Integer quantity) {

    public Quantity(Integer quantity) {
        if (quantity > 0) this.quantity = quantity;
    }
}
