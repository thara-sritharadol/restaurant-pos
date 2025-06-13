package thara.restaurant_pos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import thara.restaurant_pos.models.Category;
import thara.restaurant_pos.models.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    // Method to find all menu items by category ID
    List<MenuItem> findByCategoryId(Integer categoryId);

    // Method to find a menu item by its name
    Optional<MenuItem> findByName(String name);

    List<MenuItem> findByCategory(Category category);

    // Method to check if a menu item exists by its name
    boolean existsByName(String name);
    
}
