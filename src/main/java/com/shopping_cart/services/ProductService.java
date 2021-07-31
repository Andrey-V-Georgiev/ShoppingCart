package com.shopping_cart.services;


import com.shopping_cart.models.binding_models.ProductBindingModel;
import com.shopping_cart.models.service_models.ProductServiceModel;

public interface ProductService {


    ProductServiceModel addProduct(ProductBindingModel productBindingModel);

    ProductServiceModel findById(String id);
}
