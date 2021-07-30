package com.shopping_cart.web.rest_controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.List;

import static com.shopping_cart.constants.UserRolesConstants.HAS_ROLE_ADMIN_OR_USER;

@RestController
@RequestMapping("/cart")
@Validated
public class CartController {

    private final ModelMapper modelMapper;

    @Autowired
    public CartController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> getAllCartProducts() {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/add/{id}/{quantity}")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> addToCart(
            @PathVariable("id") @Size(min = 1)  String productId,
            BindingResult bindingResultProductId,
            @PathVariable("quantity") @Size(min = 1) String productQuantity,
            BindingResult bindingResultProductQuantity) {

        try {


            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> removeProductFromCart(
            @PathVariable("id") @Size(min = 1) String id,
            BindingResult bindingResult) {

        try {
            /* Validate fields requirements */
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(allErrors);
            }


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
