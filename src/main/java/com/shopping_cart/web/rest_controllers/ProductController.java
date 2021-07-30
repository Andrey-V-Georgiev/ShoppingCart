package com.shopping_cart.web.rest_controllers;

import com.shopping_cart.models.binding_models.ProductBindingModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

import static com.shopping_cart.constants.UserRolesConstants.*;


@RestController
@RequestMapping("/product")
@Validated
public class ProductController {

    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> getAllProduct(
            Authentication authentication) {

        try {

            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> getProductById(
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

    @PostMapping("/add")
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<?> addNewProduct(
            @Valid @RequestBody ProductBindingModel productBindingModel,
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

    @PutMapping("/edit/{id}")
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<?> editProduct(
            @PathVariable("id") @Size(min = 1) String id,
            BindingResult bindingResultPathVariable,
            @Valid @RequestBody ProductBindingModel productBindingModel,
            BindingResult bindingResultProductBindingModel) {

        try {
//            /* Validate fields requirements */
//            if (bindingResult.hasErrors()) {
//                List<ObjectError> allErrors = bindingResult.getAllErrors();
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(allErrors);
//            }


            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<?> removeProduct(
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
}
