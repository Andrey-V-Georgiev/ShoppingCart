package com.shopping_cart.repositories;


import com.shopping_cart.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    List<Optional<User>> findAllByUsername(String username);

    Optional<User> findUserByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.token = ?1 WHERE u.id = ?2")
    void updateUserToken(String newToken, String userId);
}
