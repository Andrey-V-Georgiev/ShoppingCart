package com.shopping_cart.models.entities;

import com.google.gson.annotations.Expose;
import com.shopping_cart.models.service_models.BaseServiceModel;

import java.math.BigDecimal;
import java.util.List;

public class Product extends BaseEntity {

    @Expose
    private String name;
    @Expose
    private BaseServiceModel parent;
    @Expose
    private List<Product> products;
    @Expose
    private BigDecimal price;

    public Product() {
    }

    public Product(String id) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BaseServiceModel getParent() {
        return parent;
    }

    public void setParent(BaseServiceModel parent) {
        this.parent = parent;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public String toStringAgreement() {

        StringBuilder sb = new StringBuilder();



        String stringRepresentation = sb.toString();
        return stringRepresentation;
    }

    public String toStringProduct() {

        StringBuilder sb = new StringBuilder();



        String stringRepresentation = sb.toString();
        return stringRepresentation;
    }
}
