package com.tecbom.e_commerce.Domain.Entities.Users;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.ValueObjects.CartItem;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;
import com.tecbom.e_commerce.Domain.ValueObjects.Price;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart {

    private final Cpf cpf;
    private List<CartItem> items;
    private Price price;

    public Cart(Cpf cpf) {
        this.cpf = cpf;
        this.items = new ArrayList<>();
        this.price = CalculateTotalPrice(this.items);
    }

    public Cart(Cpf cpf, List<CartItem> items) {
        this.cpf = cpf;
        this.items = items;
        this.price = CalculateTotalPrice(items);
    }

    private Price CalculateTotalPrice(List<CartItem> items) {
        Price total = new Price(BigDecimal.ZERO);
        for (CartItem item : items) {
            Price itemPrice = item.getPrice();
            total = total.add(itemPrice);
        }
        return total;
    }

    public List<CartItem> addCartItem(CartItem item) {
        this.items.add(item);
        this.price = CalculateTotalPrice(this.items);
        return this.items;
    }

    public List<CartItem> removeCartItem(Integer idxItem) {
        if (idxItem < 1 || idxItem > this.items.size()) throw new InvalidDataException("Erro");
        this.items.remove(this.items.get(idxItem - 1));
        this.price = CalculateTotalPrice(this.items);
        return this.items;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public Price getPrice() {
        return price;
    }
}
