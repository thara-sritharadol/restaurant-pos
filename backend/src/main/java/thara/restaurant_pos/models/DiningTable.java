package thara.restaurant_pos.models;

import jakarta.persistence.*;

@Entity
@Table(name = "dining_tables")
public class DiningTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number", nullable = false, unique = true)
    private String tableNumber;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ETableStatus status;

    public DiningTable() {
    }

    public DiningTable(String tableNumber, ETableStatus status) {
        this.tableNumber = tableNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public ETableStatus getStatus() {
        return status;
    }

    public void setStatus(ETableStatus status) {
        this.status = status;
    }

}
