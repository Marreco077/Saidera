package tech.buildrun.saidera.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_people")
public class People {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "hasPaid")
    private boolean hasPaid = Boolean.FALSE;

    @Column(name = "amount_to_pay", precision = 10, scale = 2)
    private BigDecimal amountToPay = BigDecimal.ZERO;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "consumption",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> consumedItems;


    public People() {
        this.consumedItems = new ArrayList<>();
    }

    public People(String name, Bill bill) {
        this();
        this.name = name;
        this.bill = bill;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public List<Item> getConsumedItems() {
        return consumedItems;
    }

    public void setConsumedItems(List<Item> consumedItems) {
        this.consumedItems = consumedItems;
    }

    // Helper methods to manage bidirectional relationships
    public void addConsumedItem(Item item) {
        if (consumedItems == null) {
            consumedItems = new ArrayList<>();
        }
        consumedItems.add(item);
        if (item.getConsumers() == null) {
            item.setConsumers(new ArrayList<>());
        }
        if (!item.getConsumers().contains(this)) {
            item.getConsumers().add(this);
        }
    }

    public void removeConsumedItem(Item item) {
        if (consumedItems != null) {
            consumedItems.remove(item);
        }
        if (item.getConsumers() != null) {
            item.getConsumers().remove(this);
        }
    }

    public BigDecimal getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(BigDecimal amountToPay) {
        this.amountToPay = amountToPay;
    }

    public void updateAmountToPay(BigDecimal amount) {
        this.amountToPay = amount;
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            this.hasPaid = true;
        }
    }
}