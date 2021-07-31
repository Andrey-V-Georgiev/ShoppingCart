package com.shopping_cart.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime addedOn;

    public Product() {
    }

    @NotEmpty(message = PRODUCT_NAME_NOT_EMPTY)
    @NotNull(message = PRODUCT_NAME_NOT_NULL)
    @Size(min = 3, max = 50, message =  PRODUCT_NAME_LENGTH)
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = PRODUCT_DESCRIPTION_NOT_EMPTY)
    @NotNull(message = PRODUCT_DESCRIPTION_NOT_NULL)
    @Size(min = 3, max = 500, message = PRODUCT_DESCRIPTION_LENGTH)
    @Column(name = "description", columnDefinition="TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = PRODUCT_PRICE_NOT_NULL)
    @DecimalMin(value = "1", message = PRODUCT_PRICE)
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "added_on", nullable = false)
    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }
}
