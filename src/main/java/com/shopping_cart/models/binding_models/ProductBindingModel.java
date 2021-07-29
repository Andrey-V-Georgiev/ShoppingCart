package com.shopping_cart.models.binding_models;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductBindingModel {

    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private List<ProductBindingModel> products;

    public ProductBindingModel(String name, BigDecimal price, List<ProductBindingModel> products) {
        this.name = name;
        this.price = price;
        this.products = new ArrayList<>(products);
    }

    @NotBlank
    @Length(min = 2)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DecimalMin(value = "0")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<ProductBindingModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBindingModel> products) {
        this.products = products;
    }
}
