package thara.restaurant_pos.repository;

import org.springframework.stereotype.Repository;

import thara.restaurant_pos.models.Category;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    public Category findByName(String name);
    public boolean existsByName(String name);
    
}
