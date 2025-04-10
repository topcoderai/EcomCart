package com.shopping.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {
    private final List<String> items;

    public ShoppingBasket() {
        this.items = new ArrayList<>();
    }

    public void addItem(String itemName) {
        items.add(itemName);
    }

    public List<String> getItems() {
        return new ArrayList<>(items);
    }
}

