package com.shopping_cart.models.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "cart_products")
public class CartProduct extends Product  {

    private Product product;
    private Integer quantity;
    private BigDecimal totalPrice;
    private BigDecimal totalPriceAfterQuantityDiscount;

    public CartProduct() {
    }

}
