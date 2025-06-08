package thara.restaurant_pos.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thara.restaurant_pos.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
    
    Optional<Order> findOrderByUserId(Integer user_id);
}
