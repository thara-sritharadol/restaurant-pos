package thara.restaurant_pos.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import thara.restaurant_pos.models.DiningTable;
import thara.restaurant_pos.models.ETableStatus;
import thara.restaurant_pos.repository.DiningTableRepository;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/dining-table")
public class DiningTableController {
    
    @Autowired
    DiningTableRepository diningTableRepository;

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> getAllDiningTables() {
        try {
            return ResponseEntity.ok(diningTableRepository.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: Could not retrieve dining tables. " + e.getMessage());
        }
    }

    @GetMapping("/{Id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> getDiningTableById(@PathVariable Long Id) {
        try {
            Optional<DiningTable> diningTable = diningTableRepository.findById(Id);
            if (diningTable.isEmpty()) {
                return ResponseEntity.status(404).body("Dining table not found.");
            }
            
            return ResponseEntity.ok().body(diningTable);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> createDiningTable(@RequestParam String tableNumber) {
        try {
            if (diningTableRepository.existsByTableNumber(tableNumber)) {
                return ResponseEntity.badRequest().body("Error: Table number already exists.");
            }

            DiningTable diningTable = new DiningTable();
            diningTable.setTableNumber(tableNumber);
            diningTable.setStatus(ETableStatus.TABLE_STATUS_AVAILABLE);
            diningTableRepository.save(diningTable);

            return ResponseEntity.status(201).body(diningTable);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: Could not create dining table. " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('STAFF')")
    public ResponseEntity<?> updateDiningTableStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            ETableStatus tableStatus;
            try {
                tableStatus = ETableStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Error: Invalid table status provided.");
            }

            return diningTableRepository.findById(id)
                .map(diningTable -> {
                    diningTable.setStatus(tableStatus);
                    diningTableRepository.save(diningTable);
                    return ResponseEntity.ok("Dining table status updated successfully.");
                })
                .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: Could not update dining table status. " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<?> deleteDiningTable(@PathVariable Long id) {
        try {
            if (!diningTableRepository.existsById(id)) {
                return ResponseEntity.badRequest().body("Error: Dining table not found.");
            }

            diningTableRepository.deleteById(id);
            return ResponseEntity.ok("Dining table deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error: Could not delete dining table. " + e.getMessage());
        }
    }
        
}
