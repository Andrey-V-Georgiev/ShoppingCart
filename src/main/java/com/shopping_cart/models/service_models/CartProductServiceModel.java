package com.shopping_cart.models.service_models;

import com.shopping_cart.models.entities.BaseEntity;

import java.math.BigDecimal;

public class CartProductServiceModel extends BaseEntity {

    private ProductServiceModel product;
    private Integer quantity;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceAfterQuantityDiscount;

    public void calculateTotalFields() {

        /* Calculate price of all products of this type without discount */
        this.totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));

        /* If user has more than one product of same type there is a 10% discount */
        if (quantity > 1) {
            this.totalPriceAfterQuantityDiscount = this.totalPrice
                    .divide(BigDecimal.valueOf(100)).multiply(BigDecimal.valueOf(90));
        } else {
            this.totalPriceAfterQuantityDiscount = this.totalPrice;
        }
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPriceAfterQuantityDiscount() {
        return totalPriceAfterQuantityDiscount;
    }

    public void setTotalPriceAfterQuantityDiscount(BigDecimal totalPriceAfterQuantityDiscount) {
        this.totalPriceAfterQuantityDiscount = totalPriceAfterQuantityDiscount;
    }
}
