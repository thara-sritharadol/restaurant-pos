package thara.restaurant_pos.controllers;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import thara.restaurant_pos.models.MenuItem;
import thara.restaurant_pos.models.Category;
import thara.restaurant_pos.repository.CategoryRepository;
import thara.restaurant_pos.repository.MenuItemRepository;
import org.springframework.web.bind.annotation.PostMapping;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/menu-item")
public class MenuItemController {

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Value("${thara.app.upload.path}")
    private String uploadPath;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> createMenuItem(
        @RequestParam("name") String name,
        @RequestParam("price") BigDecimal price,
        @RequestParam("category_name") String category_name,
        @RequestParam("description") String description,
        @RequestParam("image_file") MultipartFile imageFile,
        @RequestParam("is_available") boolean isAvailable

    ) {
        try {
            String filename = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadPath, filename);

            Path uploadDir = Paths.get(uploadPath);
            Files.createDirectories(uploadDir);

            Files.copy(imageFile.getInputStream(), filePath);

            String imageUrl = "/uploads/" + filename;

            Category category = categoryRepository.findByName(category_name);
            if (category == null) {
                throw new RuntimeException("Error: Category not found with name: " + category_name);
            }

            MenuItem menuItem = new MenuItem();
            menuItem.setName(name);
            menuItem.setPrice(price);
            menuItem.setCategory(category);
            menuItem.setDescription(description);
            menuItem.setImageUrl(imageUrl);
            menuItem.setAvailable(isAvailable);

            menuItemRepository.save(menuItem);

            return ResponseEntity.ok(menuItem);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: Could not create menu item. " + e.getMessage());
        }
    }

    
    
}
