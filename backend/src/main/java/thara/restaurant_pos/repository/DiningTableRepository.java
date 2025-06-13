package thara.restaurant_pos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import thara.restaurant_pos.models.DiningTable;

@Repository
public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {
    
    // Method to find a dining table by its table number
    Optional<DiningTable> findByTableNumber(String tableNumber);
    
    // Method to check if a dining table exists by its table number
    boolean existsByTableNumber(String tableNumber);
    
    // Method to delete a dining table by its ID
    void deleteById(Long id);

    
}