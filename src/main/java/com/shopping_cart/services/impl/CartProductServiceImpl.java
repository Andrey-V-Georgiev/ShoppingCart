package com.shopping_cart.services.impl;

import com.shopping_cart.models.entities.CartProduct;
import com.shopping_cart.models.service_models.CartProductServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.repositories.CartProductRepository;
import com.shopping_cart.services.CartProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartProductServiceImpl implements CartProductService {

    private final ModelMapper modelMapper;
    private final CartProductRepository cartProductRepository;

    @Autowired
    public CartProductServiceImpl(ModelMapper modelMapper, CartProductRepository cartProductRepository) {
        this.modelMapper = modelMapper;
        this.cartProductRepository = cartProductRepository;
    }

    @Override
    public CartProductServiceModel findByProductId(String productId) {
        return this.cartProductRepository
                .findCartProductByProductId(productId)
                .map(o -> this.modelMapper.map(o, CartProductServiceModel.class)).orElse(null);
    }

    @Override
    public CartProductServiceModel createCartProduct(ProductServiceModel productServiceModel, int quantity) {

        CartProductServiceModel cartProductServiceModel = new CartProductServiceModel();
        cartProductServiceModel.setProduct(productServiceModel);
        cartProductServiceModel.setQuantity(quantity);
        cartProductServiceModel.calculateTotalFields();
        CartProduct cartProductSaved = this.cartProductRepository
                .saveAndFlush(this.modelMapper.map(cartProductServiceModel, CartProduct.class));

        return this.modelMapper.map(cartProductSaved, CartProductServiceModel.class);
    }

    @Override
    public CartProductServiceModel persistCartProduct(CartProductServiceModel cartProductServiceModel) {
        CartProduct cartProductSaved = this.cartProductRepository
                .saveAndFlush(this.modelMapper.map(cartProductServiceModel, CartProduct.class));
        return this.modelMapper.map(cartProductSaved, CartProductServiceModel.class);
    }

    @Override
    public void removeById(String id) {
        this.cartProductRepository.deleteById(id);
    }
}
