package com.shopping_cart.models.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    private String ownerID;
    private BigDecimal totalPriceProducts;
    private BigDecimal totalPriceAfterQuantityDiscount;
    private BigDecimal totalPriceAfterAllSumDiscount;
    private BigDecimal finalDiscountInPercent;
    private BigDecimal finalDiscountInMoney;
    private Map<String, CartProduct> shoppingCartProducts;

    public Cart() {
    }

}
