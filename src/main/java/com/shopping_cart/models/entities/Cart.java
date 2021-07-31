package com.shopping_cart.models.entities;


import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    private User user;
    private Map<String, CartProduct> cartProducts = new HashMap<>();
    private BigDecimal totalPriceProducts;
    private BigDecimal totalPriceAfterQuantityDiscount;
    private BigDecimal totalPriceAfterAllSumDiscounts;
    private Double finalDiscountInPercent;
    private BigDecimal finalDiscountInMoney;

    public Cart() {
        this.setDefault();
    }

    public Cart(User user) {
        this.user = user;
        this.setDefault();
    }

    private void setDefault() {
        this.cartProducts = new HashMap<>();
        this.totalPriceProducts = BigDecimal.ZERO;
        this.totalPriceAfterQuantityDiscount = BigDecimal.ZERO;
        this.totalPriceAfterAllSumDiscounts = BigDecimal.ZERO;
        this.finalDiscountInPercent = 0.0;
        this.finalDiscountInMoney = BigDecimal.ZERO;
    }

    public void calculateTotalFields() {

        /* Calculate totalPriceProducts and totalPriceAfterQuantityDiscount */
        for (CartProduct cartProduct : cartProducts.values()) {

            this.totalPriceProducts = this.totalPriceProducts
                    .add(cartProduct.getTotalPrice());

            this.totalPriceAfterQuantityDiscount = this.totalPriceAfterQuantityDiscount
                    .add(cartProduct.getTotalPriceAfterQuantityDiscount());
        }

        /* If totalPriceAfterQuantityDiscount is more than 100 there is a 10% discount */
        if (this.totalPriceAfterQuantityDiscount.compareTo(BigDecimal.valueOf(100)) > 0) {

            this.totalPriceAfterAllSumDiscounts = this.totalPriceAfterQuantityDiscount
                    .divide(BigDecimal.valueOf(100), RoundingMode.UNNECESSARY).multiply(BigDecimal.valueOf(90));
        } else {
            this.totalPriceAfterAllSumDiscounts = this.totalPriceAfterQuantityDiscount;
        }

        /* Calculate how much is one percent of total product price before any discounts */
        BigDecimal onePercentOfTotalPriceProducts = this.totalPriceProducts
                .divide(BigDecimal.valueOf(100), RoundingMode.UNNECESSARY);

        /* Calculate finalDiscountInMoney */
        this.finalDiscountInMoney = this.totalPriceProducts.subtract(this.totalPriceAfterAllSumDiscounts);

        /* Calculate finalDiscountInPercent */
        this.finalDiscountInPercent = this.finalDiscountInMoney
                .divide(onePercentOfTotalPriceProducts, RoundingMode.UNNECESSARY).doubleValue();
    }

    public void addCartProduct(CartProduct cartProduct) {
        this.cartProducts.put(cartProduct.getProductType(), cartProduct);
    }

    @OneToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
    public BigDecimal getTotalPriceAfterAllSumDiscounts() {
        return totalPriceAfterAllSumDiscounts;
    }

    public void setTotalPriceAfterAllSumDiscounts(BigDecimal totalPriceAfterAllSumDiscounts) {
        this.totalPriceAfterAllSumDiscounts = totalPriceAfterAllSumDiscounts;
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
