package com.shopping_cart.services.impl;

import com.shopping_cart.models.entities.Cart;
import com.shopping_cart.models.entities.CartProduct;
import com.shopping_cart.models.service_models.CartProductServiceModel;
import com.shopping_cart.models.service_models.CartServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.repositories.CartProductRepository;
import com.shopping_cart.repositories.CartRepository;
import com.shopping_cart.services.CartService;
import com.shopping_cart.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Objects;

@Service
public class CartServiceImpl implements CartService {

    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private final CartProductRepository cartProductRepository;

    @Autowired
    public CartServiceImpl(ModelMapper modelMapper, CartRepository cartRepository, ProductService productService, CartProductRepository cartProductRepository) {
        this.modelMapper = modelMapper;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.cartProductRepository = cartProductRepository;
    }


    @Override
    public ProductServiceModel addProductToCart(String userId, String productId, int quantity) {

        /* Check product with this id exists */
        ProductServiceModel productServiceModel = this.productService.findById(productId);
        if (productServiceModel == null) {
            return null;
        }
        /* Prepare cartProductServiceModel */
        CartProductServiceModel cartProductServiceModel = new CartProductServiceModel();
        cartProductServiceModel.setProduct(productServiceModel);
        cartProductServiceModel.setProductType(productServiceModel.getId());
        cartProductServiceModel.setQuantity(quantity);
        cartProductServiceModel.calculateTotalFields();

        /* Save the cartProductServiceModel to DB */
        CartProduct cartProductSaved = this.cartProductRepository
                .saveAndFlush(this.modelMapper.map(cartProductServiceModel, CartProduct.class));

        /* Update the cart in DB with the added cartProduct */
        this.persistCart(userId, cartProductSaved);

        /* Return the added to cart Product */
        return productServiceModel;
    }

    @Transactional
    void persistCart(String userId, CartProduct cartProductSaved) {

        Cart cart = this.cartRepository.findCartByUserId(userId).orElse(null);
        String productType = cartProductSaved.getProductType();
        boolean alreadyContainThisProduct = cart.getCartProducts().containsKey(productType);

        if (alreadyContainThisProduct) {
            Map<String, CartProduct> cartProducts = cart.getCartProducts();
            CartProduct cartProduct = cartProducts.get(productType);
            cartProduct.addQuantity(cartProductSaved.getQuantity());
        } else {
            cart.addCartProduct(cartProductSaved);
        }

        /* Calculate total fields and save */
        cart.calculateTotalFields();
        this.cartRepository.saveAndFlush(cart);
    }
}
