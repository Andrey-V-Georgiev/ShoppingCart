package com.shopping_cart.repositories;

import com.shopping_cart.models.entities.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, String> {

    Optional<CartProduct> findCartProductByProductId(String productId);
}
