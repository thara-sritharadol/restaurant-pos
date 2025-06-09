package thara.restaurant_pos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import thara.restaurant_pos.repository.CategoryRepository;
import thara.restaurant_pos.models.Category;
import thara.restaurant_pos.payload.request.CreateCategoryRequest;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/category")
public class CategoryContoller {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.ok(categoryRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: Could not retrieve categories. " + e.getMessage());
        }
    }

    // Endpoint to create a new category
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest) {
        if (categoryRepository.existsByName(createCategoryRequest.getName())) {
            return ResponseEntity.badRequest().body("Error: Category name is already taken!");
        }

        try {
            Category category = new Category();
            category.setName(createCategoryRequest.getName());
            category.setOrder(createCategoryRequest.getSort_order());
            categoryRepository.save(category);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: Could not create category. " + e.getMessage());
        }

        return ResponseEntity.ok("Category created successfully");
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId) {
        try {
            if (!categoryRepository.existsById(categoryId)) {
                return ResponseEntity.badRequest().body("Error: Category not found!");
            }

            categoryRepository.deleteById(categoryId);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: Could not delete category. " + e.getMessage());
        }
    }
}
