package com.shopping_cart.models.service_models;

import com.shopping_cart.models.entities.BaseEntity;

public class CartProductServiceModel extends BaseEntity {

    private ProductServiceModel product;
    private Integer quantity;

    public void increaseQuantity() {
        this.quantity += 1;
    }

    public boolean decreaseQuantity() {
        if(this.quantity > 1) {
            this.quantity -= 1;
            return true;
        }
        return false;
    }

    public CartProductServiceModel() {
    }

    public ProductServiceModel getProduct() {
        return product;
    }

    public void setProduct(ProductServiceModel product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
