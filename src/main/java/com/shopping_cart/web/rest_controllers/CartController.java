package com.shopping_cart.web.rest_controllers;

import com.shopping_cart.enums.RemoveProductFromCart;
import com.shopping_cart.models.binding_models.CartProductBindingModel;
import com.shopping_cart.models.service_models.CartProductServiceModel;
import com.shopping_cart.models.service_models.CartServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.models.view_models.CartViewModel;
import com.shopping_cart.services.CartService;
import com.shopping_cart.utils.ValidationMsgUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.shopping_cart.constants.ResponseMsgConstants.*;
import static com.shopping_cart.constants.UserRoleConstants.HAS_ROLE_ADMIN_OR_USER;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final ModelMapper modelMapper;
    private final CartService cartService;
    private final ValidationMsgUtil validationMsgUtil;

    @Autowired
    public CartController(ModelMapper modelMapper, CartService cartService, ValidationMsgUtil validationMsgUtil) {
        this.modelMapper = modelMapper;
        this.cartService = cartService;
        this.validationMsgUtil = validationMsgUtil;
    }

    @GetMapping("/")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> findCart() {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            CartServiceModel cartServiceModel = this.cartService.findCartByUserId(userId);
            if (cartServiceModel == null) {
                return ResponseEntity.status(HttpStatus.OK).body(CART_NOT_FOUND);
            }
            CartViewModel cartViewModel = this.modelMapper.map(cartServiceModel, CartViewModel.class);

            return ResponseEntity.status(HttpStatus.OK).body(cartViewModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/add")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> addProductToCart(
            @Valid @RequestBody CartProductBindingModel cartProductBindingModel,
            BindingResult bindingResult) {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.validationMsgUtil.parse(allErrors));
            }
            ProductServiceModel productServiceModel = this.cartService
                    .addProductToCart(userId, cartProductBindingModel.getProductId());
            if (productServiceModel == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(PRODUCT_ADDED_TO_CART);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/decrease")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> decreaseProductCount(
            @Valid @RequestBody CartProductBindingModel cartProductBindingModel,
            BindingResult bindingResult) {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.validationMsgUtil.parse(allErrors));
            }
            RemoveProductFromCart decreaseResult = this.cartService
                    .decreaseProductCount(cartProductBindingModel.getProductId(), userId);
            switch (decreaseResult) {
                case PRODUCT_QUANTITY_DECREASED:
                    return ResponseEntity.status(HttpStatus.OK).body(PRODUCT_QUANTITY_DECREASED);
                case PRODUCT_REMOVED_FROM_CART:
                    return ResponseEntity.status(HttpStatus.OK).body(PRODUCT_REMOVED_FROM_CART);
                default:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NO_SUCH_PRODUCT_IN_CART);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> removeProductFromCart(@PathVariable("id") String cartProductId) {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            CartServiceModel cartServiceModel = this.cartService.findCartByUserId(userId);
            CartProductServiceModel cartProductServiceModel = cartServiceModel.getCartProducts()
                    .stream()
                    .filter(cp -> cp.getId().equals(cartProductId))
                    .findFirst()
                    .orElse(null);

            if (cartProductServiceModel == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
            }
            this.cartService.removeProductFromCartList(cartProductServiceModel, cartServiceModel);

            return ResponseEntity.status(HttpStatus.OK).body(PRODUCT_REMOVED_FROM_CART);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/empty")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> removeAllProducts() {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            if (this.cartService.checkCartIsEmpty(userId)) {
                return ResponseEntity.status(HttpStatus.OK).body(CART_ALREADY_EMPTY);
            }
            this.cartService.emptyTheCart(userId);

            return ResponseEntity.status(HttpStatus.OK).body(CART_EMPTY_SUCCESS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/checkout")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> checkoutCart() {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            if (this.cartService.checkCartIsEmpty(userId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CART_IS_EMPTY);
            }
            this.cartService.emptyTheCart(userId);

            return ResponseEntity.status(HttpStatus.OK).body(CART_CHECKOUT);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }
}
