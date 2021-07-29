package com.shopping_cart.repositories;

import com.shopping_cart.models.entities.BlackToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlackTokenRepository  extends JpaRepository<BlackToken, String> {

    Optional<BlackToken> findBlackTokenByToken(String token);
}
