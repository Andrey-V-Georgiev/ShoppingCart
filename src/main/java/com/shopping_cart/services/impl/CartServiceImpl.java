package com.shopping_cart.services.impl;

import com.shopping_cart.models.entities.Cart;
import com.shopping_cart.models.entities.CartProduct;
import com.shopping_cart.models.service_models.CartProductServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.repositories.CartRepository;
import com.shopping_cart.services.CartProductService;
import com.shopping_cart.services.CartService;
import com.shopping_cart.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
    public ProductServiceModel addProductToCart(String userId, String productId, int quantity) {

        /* Check product with this id exists */
        ProductServiceModel productServiceModel = this.productService.findById(productId);
        if (productServiceModel == null) {
            return null;
        }
        /* Check if the cart product exists else create new */
        CartProductServiceModel cartProductServiceModelDB = this.cartProductService.findByProductId(productId);
        CartProductServiceModel cartProductServiceModel;

        if (cartProductServiceModelDB != null) {
            /* If exists increase the quantity */
            cartProductServiceModelDB.addQuantity(quantity);
            cartProductServiceModelDB.calculateTotalFields();
            cartProductServiceModel = this.cartProductService.persistCartProduct(cartProductServiceModelDB);
        } else {
            /* If doesn't exists create new */
            cartProductServiceModel = this.cartProductService.createCartProduct(productServiceModel, quantity);
        }

        /* Update the cart in DB with the added cartProduct */
        this.persistCart(userId, cartProductServiceModel);

        /* Return the added to cart Product */
        return productServiceModel;
    }

    @Transactional
    void persistCart(String userId, CartProductServiceModel cartProductServiceModel) {

        CartProduct cartProduct = this.modelMapper.map(cartProductServiceModel, CartProduct.class);

        Cart cart = this.cartRepository.findCartByUserId(userId).orElse(null);
        boolean alreadyContainThisProduct = cart.getCartProducts().contains(cartProduct);

        if (!alreadyContainThisProduct) {
            cart.addCartProduct(cartProduct);
        }
        /* Calculate total fields and save */
        cart.calculateTotalFields();
        this.cartRepository.saveAndFlush(cart);
    }
}
