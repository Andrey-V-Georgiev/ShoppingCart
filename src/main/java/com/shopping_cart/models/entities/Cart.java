package com.shopping_cart.models.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    private User user;
    private Map<String, CartProduct> cartProducts;
    private BigDecimal totalPriceProducts;
    private BigDecimal totalPriceAfterQuantityDiscount;
    private BigDecimal totalPriceAfterAllSumDiscount;
    private Double finalDiscountInPercent;
    private BigDecimal finalDiscountInMoney;

    public Cart() {
    }

    @OneToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "cart_cart_mproduct_mapping",
            joinColumns = {@JoinColumn(name = "cart_product_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "cart_id", referencedColumnName = "id")})
    @MapKey(name = "id")
    public Map<String, CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(Map<String, CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @DecimalMin(value = "0")
    public BigDecimal getTotalPriceProducts() {
        return totalPriceProducts;
    }

    public void setTotalPriceProducts(BigDecimal totalPriceProducts) {
        this.totalPriceProducts = totalPriceProducts;
    }

    @DecimalMin(value = "0")
    public BigDecimal getTotalPriceAfterQuantityDiscount() {
        return totalPriceAfterQuantityDiscount;
    }

    public void setTotalPriceAfterQuantityDiscount(BigDecimal totalPriceAfterQuantityDiscount) {
        this.totalPriceAfterQuantityDiscount = totalPriceAfterQuantityDiscount;
    }

    @DecimalMin(value = "0")
    public BigDecimal getTotalPriceAfterAllSumDiscount() {
        return totalPriceAfterAllSumDiscount;
    }

    public void setTotalPriceAfterAllSumDiscount(BigDecimal totalPriceAfterAllSumDiscount) {
        this.totalPriceAfterAllSumDiscount = totalPriceAfterAllSumDiscount;
    }

    @DecimalMin(value = "0")
    public Double getFinalDiscountInPercent() {
        return finalDiscountInPercent;
    }

    public void setFinalDiscountInPercent(Double finalDiscountInPercent) {
        this.finalDiscountInPercent = finalDiscountInPercent;
    }

    @DecimalMin(value = "0")
    public BigDecimal getFinalDiscountInMoney() {
        return finalDiscountInMoney;
    }

    public void setFinalDiscountInMoney(BigDecimal finalDiscountInMoney) {
        this.finalDiscountInMoney = finalDiscountInMoney;
    }
}
