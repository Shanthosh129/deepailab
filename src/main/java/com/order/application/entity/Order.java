package com.order.application.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String itemName;
    private int quantity;
    private String shippingAddress;
    @Column(nullable = false)
    private String status; // Possible values: NEW, DISPATCHED, CANCELLED
    private LocalDateTime placementTimestamp;
    private String referenceNumber;

    @PrePersist
    public void generateReferenceNumber() {
        this.referenceNumber = "ORDER-" + System.currentTimeMillis();
    }
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPlacementTimestamp() {
        return placementTimestamp;
    }

    public void setPlacementTimestamp(LocalDateTime placementTimestamp) {
        this.placementTimestamp = placementTimestamp;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
