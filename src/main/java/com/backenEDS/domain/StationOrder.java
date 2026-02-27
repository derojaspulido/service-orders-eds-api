package com.backenEDS.domain;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.backenEDS.domain.enums.OrderStatus;
import com.backenEDS.domain.enums.OrderType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * Entity representing a service order created by a station.
 *
 * This entity is persisted in the "station_orders" table.
 * 
 * Business constraints:
 * - id is auto-generated as UUID.
 * - stationId is mandatory.
 * - type is mandatory.
 * - status is mandatory.
 * - createdAt and updatedAt are automatically managed by Spring Data JPA auditing.
 */
@Entity
@Table(
    name = "station_orders",
    indexes = {
        @Index(name = "idx_station_orders_station_id", columnList = "stationId"),
        @Index(name = "idx_station_orders_status", columnList = "status"),
        @Index(name = "idx_station_orders_created_at", columnList = "createdAt")
    }
)
@EntityListeners(AuditingEntityListener.class)
public class StationOrder {

    /**
     * Unique identifier of the service order.
     * Generated automatically using UUID.
     */
    @Id
    @UuidGenerator
    @Column(nullable = false, updatable = false)
    private UUID id;

    /**
     * Identifier of the station that created the order.
     * This field is mandatory.
     */
    @Column(nullable = false, length = 100)
    private String stationId;

    /**
     * Type of the order.
     * Stored as STRING to preserve enum readability in the database.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderType type;

    /**
     * Optional textual description of the order.
     */
    @Column(length = 1000)
    private String description;

    /**
     * Current status of the order.
     * Stored as STRING for clarity and maintainability.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status;

    /**
     * Timestamp when the order was created.
     * Automatically populated by Spring Data JPA auditing.
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    /**
     * Timestamp when the order was last modified.
     * Automatically updated by Spring Data JPA auditing.
     */
    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;

    @PrePersist
    public void onCreate() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}