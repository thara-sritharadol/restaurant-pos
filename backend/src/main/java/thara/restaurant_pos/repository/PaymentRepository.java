package thara.restaurant_pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thara.restaurant_pos.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

    Optional<Payment> findById(Long paymnetId);
    
}
