package com.tecbom.e_commerce.Domain.ValueObjects;

public class CartItem {

    private Integer id;
    private ValidText name;
    private Quantity quantity;
    private Price price;

    public CartItem(Integer id, ValidText name, Quantity quantity, Price price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Price getPrice() {
        return price;
    }

    public ValidText getName() {
        return name;
    }
}
