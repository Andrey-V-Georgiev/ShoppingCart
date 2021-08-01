package com.shopping_cart.models.view_models;

import com.shopping_cart.models.service_models.CartProductServiceModel;

import java.math.BigDecimal;
import java.util.List;

public class CartViewModel {

    private UserViewModel user;
    private List<CartProductServiceModel> cartProducts;
    private BigDecimal totalPriceProducts;
    private BigDecimal totalPriceAfterQuantityDiscount;
    private BigDecimal totalPriceAfterAllSumDiscounts;
    private Double finalDiscountInPercent;
    private BigDecimal finalDiscountInMoney;

    public CartViewModel() {
    }

    public CartViewModel(UserViewModel user, List<CartProductServiceModel> cartProducts, BigDecimal totalPriceProducts, BigDecimal totalPriceAfterQuantityDiscount, BigDecimal totalPriceAfterAllSumDiscounts, Double finalDiscountInPercent, BigDecimal finalDiscountInMoney) {
        this.user = user;
        this.cartProducts = cartProducts;
        this.totalPriceProducts = totalPriceProducts;
        this.totalPriceAfterQuantityDiscount = totalPriceAfterQuantityDiscount;
        this.totalPriceAfterAllSumDiscounts = totalPriceAfterAllSumDiscounts;
        this.finalDiscountInPercent = finalDiscountInPercent;
        this.finalDiscountInMoney = finalDiscountInMoney;
    }

    public UserViewModel getUser() {
        return user;
    }

    public void setUser(UserViewModel user) {
        this.user = user;
    }

    public List<CartProductServiceModel> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductServiceModel> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public BigDecimal getTotalPriceProducts() {
        return totalPriceProducts;
    }

    public void setTotalPriceProducts(BigDecimal totalPriceProducts) {
        this.totalPriceProducts = totalPriceProducts;
    }

    public BigDecimal getTotalPriceAfterQuantityDiscount() {
        return totalPriceAfterQuantityDiscount;
    }

    public void setTotalPriceAfterQuantityDiscount(BigDecimal totalPriceAfterQuantityDiscount) {
        this.totalPriceAfterQuantityDiscount = totalPriceAfterQuantityDiscount;
    }

    public BigDecimal getTotalPriceAfterAllSumDiscounts() {
        return totalPriceAfterAllSumDiscounts;
    }

    public void setTotalPriceAfterAllSumDiscounts(BigDecimal totalPriceAfterAllSumDiscounts) {
        this.totalPriceAfterAllSumDiscounts = totalPriceAfterAllSumDiscounts;
    }

    public Double getFinalDiscountInPercent() {
        return finalDiscountInPercent;
    }

    public void setFinalDiscountInPercent(Double finalDiscountInPercent) {
        this.finalDiscountInPercent = finalDiscountInPercent;
    }

    public BigDecimal getFinalDiscountInMoney() {
        return finalDiscountInMoney;
    }

    public void setFinalDiscountInMoney(BigDecimal finalDiscountInMoney) {
        this.finalDiscountInMoney = finalDiscountInMoney;
    }
}
