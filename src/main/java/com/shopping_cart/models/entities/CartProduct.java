package com.shopping_cart.models.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

@Entity
@Table(name = "cart_products")
public class CartProduct extends BaseEntity  {

    private Product product;
    private Integer quantity;

    public CartProduct() {
    }

    @OneToOne(targetEntity=Product.class, cascade = CascadeType.MERGE)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @NotNull(message = ORDERED_QUANTITY_NOT_NULL)
    @Min(value = 0 , message = ORDERED_QUANTITY_CANNOT_BE_NEGATIVE)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
