package com.shopping_cart.web.rest_controllers;

import com.shopping_cart.models.binding_models.ProductBindingModel;
import com.shopping_cart.models.service_models.ProductServiceModel;
import com.shopping_cart.services.ProductService;
import com.shopping_cart.utils.ValidationMsgUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.shopping_cart.constants.ResponseMsgConstants.*;
import static com.shopping_cart.constants.UserRoleConstants.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ValidationMsgUtil validationMsgUtil;

    @Autowired
    public ProductController(ProductService productService, ValidationMsgUtil validationMsgUtil) {
        this.productService = productService;
        this.validationMsgUtil = validationMsgUtil;
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> findProductById(@PathVariable("id") String id) {

        try {
            /* Find product by id */
            ProductServiceModel productServiceModel = this.productService.findById(id);

            /* If no such a product */
            if (productServiceModel == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(productServiceModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<?> findProductsByKeyword(@PathVariable("keyword") String keyword) {

        try {
            /* Find product by keyword */
            List<ProductServiceModel> productServiceModels = this.productService.findByKeyword(keyword);

            /* If no such a product */
            if (productServiceModels.size() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCTS_SEARCH_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(productServiceModels);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/all")
    // @PreAuthorize(HAS_ROLE_ADMIN_OR_USER)
    public ResponseEntity<?> findAllProduct() {

        try {
            /* Find all products */
            List<ProductServiceModel> productServiceModelAll = this.productService.findAll();

            /* If no such a product */
            if (productServiceModelAll.size() == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCTS_NOT_FOUND);
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(productServiceModelAll);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<?> addNewProduct(
            @Valid @RequestBody ProductBindingModel productBindingModel,
            BindingResult bindingResult) {

        try {
            /* Validate input */
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.validationMsgUtil.parse(allErrors));
            }
            /* Create new product */
            ProductServiceModel productServiceModel = this.productService.addProduct(productBindingModel);

            /* If product already exists */
            if (productServiceModel == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(PRODUCT_ALREADY_EXISTS);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(productServiceModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<?> editProduct(
            @PathVariable("id") String id,
            @Valid @RequestBody ProductBindingModel productBindingModel,
            BindingResult bindingResult) {

        try {
            /* Validate input */
            if (bindingResult.hasErrors()) {
                List<ObjectError> allErrors = bindingResult.getAllErrors();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.validationMsgUtil.parse(allErrors));
            }
            /* Edit product */
            ProductServiceModel productServiceModel = this.productService.editProduct(id, productBindingModel);

            /* If no such a product */
            if (productServiceModel == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
            }

            return ResponseEntity.status(HttpStatus.OK).body(productServiceModel);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/remove/{id}")
    @PreAuthorize(HAS_ROLE_ADMIN)
    public ResponseEntity<?> removeProduct(@PathVariable("id") String id) {

        try {
            /* Remove product by id */
            int deletionResult = this.productService.removeById(id);

            /* If no such a product */
            if (deletionResult < 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PRODUCT_NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).body(PRODUCT_REMOVED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FRIENDLY_INTERNAL_SERVER_ERROR);
        }
    }
}
