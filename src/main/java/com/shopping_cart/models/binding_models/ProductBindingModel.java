package com.shopping_cart.models.binding_models;


import java.math.BigDecimal;

public class ProductBindingModel {

    private String name;
    private BigDecimal price;

    public ProductBindingModel(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
