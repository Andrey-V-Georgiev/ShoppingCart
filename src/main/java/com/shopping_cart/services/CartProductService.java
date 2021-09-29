package com.shopping_cart.services;

import com.shopping_cart.models.service_models.CartProductServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;

public interface CartProductService {

    CartProductServiceModel createCartProduct(ProductServiceModel productServiceModel);

    void removeById(String id);
}
