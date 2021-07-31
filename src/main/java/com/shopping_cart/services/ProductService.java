package com.shopping_cart.services;


import com.shopping_cart.models.binding_models.ProductBindingModel;
import com.shopping_cart.models.service_models.ProductServiceModel;

import java.util.List;

public interface ProductService {


    ProductServiceModel addProduct(ProductBindingModel productBindingModel);

    ProductServiceModel findById(String id);

    List<ProductServiceModel> findAll();

    ProductServiceModel editProduct(String id, ProductBindingModel productBindingModel);

    int removeById(String id);
}
