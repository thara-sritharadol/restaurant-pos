package thara.restaurant_pos.controllers;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import thara.restaurant_pos.models.MenuItem;
import thara.restaurant_pos.dto.MenuItemDTO;
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

    private MenuItemDTO mapToMenuItemDTO(MenuItem menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setPrice(menuItem.getPrice());
        if (menuItem.getCategory() != null) {
            dto.setCategoryId(menuItem.getCategory().getId());
            dto.setCategoryName(menuItem.getCategory().getName());
        }
        dto.setDescription(menuItem.getDescription());
        dto.setImageUrl(menuItem.getImageUrl());
        dto.setAvailable(menuItem.isAvailable());
        return dto;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> getAllMenuItems() {
        try {
            List<MenuItem> menuItems = menuItemRepository.findAll();
            List<MenuItemDTO> menuItemDTOs = menuItems.stream().map(this::mapToMenuItemDTO).toList();
            return ResponseEntity.ok().body(menuItemDTOs);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: Could not retrieve menu items. " + e.getMessage());
        }
    }

    @PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
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

    @DeleteMapping("/{menuItemId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Integer menuItemId) {
        try {
            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new RuntimeException("Error: Menu item not found with id: " + menuItemId));

            menuItemRepository.delete(menuItem);
            return ResponseEntity.ok("Menu item deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: Could not delete menu item. " + e.getMessage());
        }
    }

    
    
}
