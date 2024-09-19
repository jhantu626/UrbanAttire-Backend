package io.app.repository;

import io.app.models.Orders;
import io.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.util.Streamable;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,String> {
    List<Orders> findByUser(User user);
}
