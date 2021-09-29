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
    public RemoveProductFromCart decreaseProductCount(String productId, String userId, int quantity) {

        CartServiceModel cartServiceModel = this.findCartByUserId(userId);
        List<CartProductServiceModel> cartProducts = cartServiceModel.getCartProducts();
        CartProductServiceModel cartProductServiceModel = cartProducts
                .stream().filter(cp -> cp.getProduct().getId().equals(productId)).findFirst().orElse(null);
        if (cartProductServiceModel == null) {
            return RemoveProductFromCart.PRODUCT_NOT_FOUND;
        }
        boolean quantityMoreThanAvailable = cartProductServiceModel.getQuantity() < quantity;
        boolean quantityEnoughForDecrease = cartProductServiceModel.getQuantity() > quantity;
        if (quantityMoreThanAvailable) {
            return RemoveProductFromCart.QUANTITY_MORE_THAN_AVAILABLE;
        } else if (quantityEnoughForDecrease) {
            this.decreaseProductQuantity(userId, cartProductServiceModel, quantity);
            return RemoveProductFromCart.PRODUCT_QUANTITY_DECREASED;
        } else {
            this.removeProductFromCartList(cartProductServiceModel, cartProducts, cartServiceModel);
            return RemoveProductFromCart.DEFAULT;
        }
    }

    @Transactional
    void decreaseProductQuantity(
            String userId,
            CartProductServiceModel cartProductServiceModel,
            int quantity) {

        cartProductServiceModel.decreaseQuantity(quantity);
        cartProductServiceModel.calculateTotalFields();
        this.cartProductService.persistCartProduct(cartProductServiceModel);
        CartServiceModel cartServiceModel = this.cartRepository
                .findCartByUserId(userId).map(o -> this.modelMapper.map(o, CartServiceModel.class)).orElse(null);
        cartServiceModel.calculateTotalFields();
        this.cartRepository.saveAndFlush(this.modelMapper.map(cartServiceModel, Cart.class));
    }

    @Override
    public void removeProductFromCartList(
            CartProductServiceModel cartProductServiceModel,
            List<CartProductServiceModel> cartProducts,
            CartServiceModel cartServiceModel) {

        this.cartProductService.removeById(cartProductServiceModel.getId());
        cartProducts.remove(cartProductServiceModel);
        cartServiceModel.calculateTotalFields();
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


    @Override
    public ProductServiceModel addProductToCart(String userId, String productId, int quantity) {
        ProductServiceModel productServiceModel = this.productService.findById(productId);
        if (productServiceModel == null) {
            return null;
        }
        CartProductServiceModel cartProductServiceModel = this.cartProductCreateOrUpdate(productId, quantity, productServiceModel);
        this.updateCart(userId, cartProductServiceModel);
        return productServiceModel;
    }

    private CartProductServiceModel cartProductCreateOrUpdate(
            String productId,
            Integer quantity,
            ProductServiceModel productServiceModel) {

        CartProductServiceModel cartProductServiceModelDB = this.cartProductService.findByProductId(productId);
        CartProductServiceModel cartProductServiceModel;
        if (cartProductServiceModelDB != null) {
            cartProductServiceModelDB.addQuantity(quantity);
            cartProductServiceModelDB.calculateTotalFields();
            cartProductServiceModel = this.cartProductService.persistCartProduct(cartProductServiceModelDB);
        } else {
            cartProductServiceModel = this.cartProductService.createCartProduct(productServiceModel, quantity);
        }
        return cartProductServiceModel;
    }

    @Transactional
    void updateCart(String userId, CartProductServiceModel cartProductServiceModel) {
        CartServiceModel cartServiceModel = this.cartRepository
                .findCartByUserId(userId).map(o -> this.modelMapper.map(o, CartServiceModel.class)).orElse(null);
        assert cartServiceModel != null;
        boolean alreadyContainThisProduct = cartServiceModel.getCartProducts().contains(cartProductServiceModel);
        if (!alreadyContainThisProduct) {
            cartServiceModel.addCartProduct(cartProductServiceModel);
        }
        cartServiceModel.calculateTotalFields();
        this.cartRepository.saveAndFlush(this.modelMapper.map(cartServiceModel, Cart.class));
    }
}
