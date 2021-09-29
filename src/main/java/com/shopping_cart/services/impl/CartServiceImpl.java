package com.shopping_cart.services.impl;

import com.shopping_cart.enums.RemoveProductFromCart;
import com.shopping_cart.models.entities.Cart;
import com.shopping_cart.models.service_models.CartProductServiceModel;
import com.shopping_cart.models.service_models.CartServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.repositories.CartRepository;
import com.shopping_cart.services.CartProductService;
import com.shopping_cart.services.CartService;
import com.shopping_cart.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartProductService cartProductService;

    @Autowired
    public CartServiceImpl(ModelMapper modelMapper, CartRepository cartRepository, ProductService productService, CartProductService cartProductService) {
        this.modelMapper = modelMapper;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartProductService = cartProductService;
    }

    @Override
    public CartServiceModel findCartByUserId(String userId) {
        return this.cartRepository.findCartByUserId(userId)
                .map(o -> this.modelMapper.map(o, CartServiceModel.class)).orElse(null);
    }

    @Override
    public ProductServiceModel addProductToCart(String userId, String productId) {

        ProductServiceModel productServiceModel = this.productService.findById(productId);
        if (productServiceModel == null) {
            return null;
        }
        CartServiceModel cartServiceModel = this.findCartByUserId(userId);
        CartProductServiceModel cartProductServiceModel = cartServiceModel.getCartProducts()
                .stream()
                .filter(cp -> cp.getProduct().getId() == productId)
                .findFirst()
                .orElse(null);

        if (cartProductServiceModel != null) {
            cartProductServiceModel.increaseQuantity();
        } else {
            CartProductServiceModel cartProductServiceModelNew = this.cartProductService.createCartProduct(productServiceModel);
            cartServiceModel.addCartProduct(cartProductServiceModelNew);
        }
        this.cartRepository.saveAndFlush(this.modelMapper.map(cartServiceModel, Cart.class));
        return productServiceModel;
    }

    @Override
    public RemoveProductFromCart decreaseProductCount(String productId, String userId) {

        ProductServiceModel productServiceModel = this.productService.findById(productId);
        if (productServiceModel == null) {
            return null;
        }
        CartServiceModel cartServiceModel = this.findCartByUserId(userId);
        CartProductServiceModel cartProductServiceModel = cartServiceModel.getCartProducts()
                .stream()
                .filter(cp -> cp.getProduct().getId() == productId)
                .findFirst()
                .orElse(null);

        if (cartProductServiceModel == null) {
            return RemoveProductFromCart.PRODUCT_NOT_FOUND;
        }
        boolean quantityDecreased = cartProductServiceModel.decreaseQuantity();
        if (quantityDecreased) {
            this.cartRepository.saveAndFlush(this.modelMapper.map(cartServiceModel, Cart.class));
            return RemoveProductFromCart.PRODUCT_QUANTITY_DECREASED;
        } else {
            this.removeProductFromCartList(cartProductServiceModel, cartServiceModel);
            return RemoveProductFromCart.PRODUCT_REMOVED_FROM_CART;
        }
    }

    @Override
    public void removeProductFromCartList(
            CartProductServiceModel cartProductServiceModel,
            CartServiceModel cartServiceModel) {

        this.cartProductService.removeById(cartProductServiceModel.getId());
        cartServiceModel.getCartProducts().remove(cartProductServiceModel);
        this.cartRepository.saveAndFlush(this.modelMapper.map(cartServiceModel, Cart.class));
    }

    @Override
    public void emptyTheCart(String userId) {
        CartServiceModel cartServiceModel = this.findCartByUserId(userId);
        List<CartProductServiceModel> cartProducts = cartServiceModel.getCartProducts();
        for (CartProductServiceModel cartProduct : cartProducts) {
            this.cartProductService.removeById(cartProduct.getId());
        }
        cartServiceModel.reset();
        this.cartRepository.saveAndFlush(this.modelMapper.map(cartServiceModel, Cart.class));
    }

    @Override
    public boolean checkCartIsEmpty(String userId) {
        CartServiceModel cartServiceModel = this.findCartByUserId(userId);
        List<CartProductServiceModel> cartProducts = cartServiceModel.getCartProducts();
        return cartProducts.size() == 0;
    }
}
