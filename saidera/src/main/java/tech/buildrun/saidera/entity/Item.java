package tech.buildrun.saidera.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unique_id", nullable = false)
    private String uniqueId = UUID.randomUUID().toString();

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @ManyToMany(mappedBy = "consumedItems")
    private List<People> consumers;


    public Item() {
        this.consumers = new ArrayList<>();
    }

    public Item(String name, BigDecimal price, Integer quantity, Bill bill) {
        this();
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.bill = bill;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public List<People> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<People> consumers) {
        this.consumers = consumers;
    }

    // Helper methods to manage bidirectional relationships
    public void addConsumer(People person) {
        if (consumers == null) {
            consumers = new ArrayList<>();
        }
        consumers.add(person);
        if (person.getConsumedItems() == null) {
            person.setConsumedItems(new ArrayList<>());
        }
        if (!person.getConsumedItems().contains(this)) {
            person.getConsumedItems().add(this);
        }
    }

    public void removeConsumer(People person) {
        if (consumers != null) {
            consumers.remove(person);
        }
        if (person.getConsumedItems() != null) {
            person.getConsumedItems().remove(this);
        }
    }
}