package com.shopping_cart.web.rest_controllers;

import com.shopping_cart.models.binding_models.CartAddBindingModel;
import com.shopping_cart.models.service_models.CartServiceModel;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.models.view_models.CartViewModel;
import com.shopping_cart.services.CartService;
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

    @Autowired
    public CartController(ModelMapper modelMapper, CartService cartService) {
        this.modelMapper = modelMapper;
        this.cartService = cartService;
    }

    @GetMapping("/")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> findCart() {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            /* Find cart */
            CartServiceModel cartServiceModel = this.cartService.findByUserId(userId);

            /* If cannot find cart */
            if (cartServiceModel == null) {
                return ResponseEntity.status(HttpStatus.OK).body(CART_NOT_FOUND);
            }
            /* Return only necessary fields */
            CartViewModel cartViewModel = this.modelMapper.map(cartServiceModel, CartViewModel.class);

            return ResponseEntity.status(HttpStatus.OK).body(cartViewModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/add")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> addToCart(
            @Valid @RequestBody CartAddBindingModel cartAddBindingModel,
            BindingResult bindingResult ) {

        String userId = String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        try {
            /* Validate input */
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(allErrors);
            }
            /* Add the product to cart */
            ProductServiceModel productServiceModel = this.cartService
                    .addProductToCart(userId, cartAddBindingModel.getProductId(), cartAddBindingModel.getQuantity());

            /* If no such a product */
            if (productServiceModel == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(productServiceModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> removeProductFromCart(@PathVariable("id") String id) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/clear")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> clearAllProducts() {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
