package com.shopping_cart.models.entities;


import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

@Entity
@Table(name = "cart_products")
public class CartProduct extends BaseEntity  {

    private Product product;
    private Integer quantity;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceAfterQuantityDiscount;

    public CartProduct() {
    }

    @OneToOne(targetEntity=Product.class,cascade=CascadeType.MERGE)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @NotNull(message = ORDERED_QUANTITY_NOT_NULL)
    @Min(value = 1 , message = ORDERED_QUANTITY_MUST_BE_AT_LEAST_ONE)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @DecimalMin(value = "0")
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @DecimalMin(value = "0")
    public BigDecimal getTotalPriceAfterQuantityDiscount() {
        return totalPriceAfterQuantityDiscount;
    }

    public void setTotalPriceAfterQuantityDiscount(BigDecimal totalPriceAfterQuantityDiscount) {
        this.totalPriceAfterQuantityDiscount = totalPriceAfterQuantityDiscount;
    }
}
