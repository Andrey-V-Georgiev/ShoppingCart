package com.shopping_cart.models.binding_models;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

import static com.shopping_cart.constants.ModelsMsgConstants.*;

public class ProductBindingModel {

    private String name;
    private String description;
    private String pictureUrl;
    private BigDecimal price;


    @NotEmpty(message = PRODUCT_NAME_NOT_EMPTY)
    @NotNull(message = PRODUCT_NAME_NOT_NULL)
    @Size(min = 3, max = 50, message = PRODUCT_NAME_LENGTH)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty(message = PRODUCT_DESCRIPTION_NOT_EMPTY)
    @NotNull(message = PRODUCT_DESCRIPTION_NOT_NULL)
    @Size(min = 3, message = PRODUCT_DESCRIPTION_LENGTH)
    public String getDescription() {
        return description;
    }

    @NotEmpty(message = PRODUCT_PICTURE_URL_NOT_EMPTY)
    @NotNull(message = PRODUCT_PICTURE_URL_NOT_NULL)
    @Size(min = 3, message = PRODUCT_PICTURE_URL_LENGTH)
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = PRODUCT_PRICE_NOT_NULL)
    @DecimalMin(value = "1", message = PRODUCT_PRICE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
