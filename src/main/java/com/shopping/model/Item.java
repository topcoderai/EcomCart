package com.shopping.model;

public class Item {
    private final String name;
    private final int priceInPence;

    public Item(String name, int priceInPence) {
        this.name = name;
        this.priceInPence = priceInPence;
    }

    public String getName() {
        return name;
    }

    public int getPriceInPence() {
        return priceInPence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}