package io.app.repository;

import io.app.models.Cart;
import io.app.models.Product;
import io.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByUser(User user);
    Cart findByUserAndProduct(User user, Product product);
}
