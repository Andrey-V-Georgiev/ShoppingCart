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
import java.util.ArrayList;
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
    public RemoveProductFromCart removeProduct(String productId, String userId, int quantity) {

        /* Get the cartProducts of user cart */
        CartServiceModel cartServiceModel = this.findCartByUserId(userId);
        List<CartProductServiceModel> cartProducts = cartServiceModel.getCartProducts();

        /* Find the cart product which contains this type of product */
        CartProductServiceModel cartProductServiceModel = cartProducts
                .stream().filter(cp -> cp.getProduct().getId().equals(productId)).findFirst().orElse(null);

        /* If cart doesn't contains this product */
        if (cartProductServiceModel == null) {
            return RemoveProductFromCart.PRODUCT_NOT_FOUND;
        }
        /* Define the three possible cases */
        boolean quantityMoreThanAvailable = cartProductServiceModel.getQuantity() < quantity;
        boolean quantityEnoughForDecrease = cartProductServiceModel.getQuantity() > quantity;

        if (quantityMoreThanAvailable) {
            /* If try to decrease with more than the available quantity */
            return RemoveProductFromCart.QUANTITY_MORE_THAN_AVAILABLE;

        } else if (quantityEnoughForDecrease) {
            /* Want to decrease the quantity without removing the product from cart */
            this.decreaseProductQuantity(cartProductServiceModel, quantity);
            return RemoveProductFromCart.PRODUCT_QUANTITY_DECREASED;

        } else {
            /* Want to remove the product from the cart */
            this.removeProductFromCartList(cartProductServiceModel, cartProducts, cartServiceModel);
            return RemoveProductFromCart.DEFAULT;
        }
    }

    private void decreaseProductQuantity(
            CartProductServiceModel cartProductServiceModel,
            int quantity) {

        cartProductServiceModel.decreaseQuantity(quantity);
        cartProductServiceModel.calculateTotalFields();
        this.cartProductService.persistCartProduct(cartProductServiceModel);
    }

    private void removeProductFromCartList(
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

        /* Get the cartProducts of user cart */
        CartServiceModel cartServiceModel = this.findCartByUserId(userId);
        List<CartProductServiceModel> cartProducts = cartServiceModel.getCartProducts();

        /* Delete all cartProducts from cart */
        for (CartProductServiceModel cartProduct : cartProducts) {
            this.cartProductService.removeById(cartProduct.getId());
        }
        /* Set empty list to cart */
        cartServiceModel.setCartProducts(new ArrayList<>());

        /* Save changes */
        this.cartRepository.saveAndFlush(this.modelMapper.map(cartServiceModel, Cart.class));
    }

    @Override
    public boolean checkCartIsEmpty(String userId) {

        /* Get the cartProducts of user cart */
        CartServiceModel cartServiceModel = this.findCartByUserId(userId);
        List<CartProductServiceModel> cartProducts = cartServiceModel.getCartProducts();

        /* If cart is empty return true */
        return cartProducts.size() == 0;
    }

    @Override
    public ProductServiceModel addProductToCart(String userId, String productId, int quantity) {

        /* Check product with this id exists */
        ProductServiceModel productServiceModel = this.productService.findById(productId);
        if (productServiceModel == null) {
            return null;
        }
        /* Handle the cartProduct */
        CartProductServiceModel cartProductServiceModel = this.cartProductCreateOrUpdate(productId, quantity, productServiceModel);

        /* Handle the cart */
        this.updateCart(userId, cartProductServiceModel);

        /* Return the added to cart Product */
        return productServiceModel;
    }

    private CartProductServiceModel cartProductCreateOrUpdate(
            String productId,
            Integer quantity,
            ProductServiceModel productServiceModel) {

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
        return cartProductServiceModel;
    }

    @Transactional
    void updateCart(String userId, CartProductServiceModel cartProductServiceModel) {

        /* Find user cart */
        CartServiceModel cartServiceModel = this.cartRepository
                .findCartByUserId(userId).map(o -> this.modelMapper.map(o, CartServiceModel.class)).orElse(null);

        /* Check if the cart already contains this cart product */
        assert cartServiceModel != null;
        boolean alreadyContainThisProduct = cartServiceModel.getCartProducts().contains(cartProductServiceModel);

        /* If cart doesn't add it */
        if (!alreadyContainThisProduct) {
            cartServiceModel.addCartProduct(cartProductServiceModel);
        }
        /* Calculate total fields and save */
        cartServiceModel.calculateTotalFields();

        /* Save changes to DB */
        this.cartRepository.saveAndFlush(this.modelMapper.map(cartServiceModel, Cart.class));
    }
}
