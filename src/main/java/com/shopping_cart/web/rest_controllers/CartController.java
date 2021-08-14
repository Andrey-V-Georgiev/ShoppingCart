package com.shopping_cart.web.rest_controllers;

import com.shopping_cart.enums.RemoveProductFromCart;
import com.shopping_cart.models.binding_models.CartBindingModel;
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
            /* Find cart */
            CartServiceModel cartServiceModel = this.cartService.findCartByUserId(userId);

            /* If cannot find cart */
            if (cartServiceModel == null) {
                return ResponseEntity.status(HttpStatus.OK).body(CART_NOT_FOUND);
            }
            /* Return only necessary fields */
            CartViewModel cartViewModel = this.modelMapper.map(cartServiceModel, CartViewModel.class);

            return ResponseEntity.status(HttpStatus.OK).body(cartViewModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/add")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> addToCart(
            @Valid @RequestBody CartBindingModel cartBindingModel,
            BindingResult bindingResult) {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            /* Validate input */
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.validationMsgUtil.parse(allErrors));
            }
            /* Add the product to cart */
            ProductServiceModel productServiceModel = this.cartService
                    .addProductToCart(userId, cartBindingModel.getProductId(), cartBindingModel.getQuantity());

            /* If no such a product */
            if (productServiceModel == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(productServiceModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/remove")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> removeProductFromCart(
            @Valid @RequestBody CartBindingModel cartBindingModel,
            BindingResult bindingResult) {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            /* Validate input */
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.validationMsgUtil.parse(allErrors));
            }
            /* Remove product by id */
            RemoveProductFromCart deletionResult = this.cartService
                    .removeProduct(cartBindingModel.getProductId(), userId, cartBindingModel.getQuantity());

            switch (deletionResult) {
                case PRODUCT_NOT_FOUND:
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NO_SUCH_PRODUCT_IN_CART);

                case QUANTITY_MORE_THAN_AVAILABLE:
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(QUANTITY_MORE_THAN_AVAILABLE);

                case PRODUCT_QUANTITY_DECREASED:
                    return ResponseEntity.status(HttpStatus.OK).body(PRODUCT_QUANTITY_DECREASED);

                default:
                    return ResponseEntity.status(HttpStatus.OK).body(PRODUCT_REMOVED_FROM_CART);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/empty")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> removeAllProducts() {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            /* Check for cart already empty */
            if(this.cartService.checkCartIsEmpty(userId)) {
                return ResponseEntity.status(HttpStatus.OK).body(CART_ALREADY_EMPTY);
            }
            /* Remove all products from cart */
            this.cartService.emptyTheCart(userId);

            return ResponseEntity.status(HttpStatus.OK).body(CART_EMPTY_SUCCESS);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }
}
