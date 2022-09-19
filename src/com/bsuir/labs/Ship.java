package com.bsuir.labs;

public class Ship {
    private String product;
    private String name;

    public Ship(String name) {
        this.name = name;
    }

    public Ship() {
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "name='" + name + '\'' +
                '}';
    }
}
