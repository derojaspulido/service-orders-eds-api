package com.backenEDS.dto;

import java.time.Instant;
import java.util.UUID;

import com.backenEDS.domain.enums.OrderStatus;
import com.backenEDS.domain.enums.OrderType;

public class StationOrderResponseDTO {

    private UUID id;
    private String stationId;
    private OrderType type;
    private String description;
    private OrderStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getStationId() { return stationId; }
    public void setStationId(String stationId) { this.stationId = stationId; }

    public OrderType getType() { return type; }
    public void setType(OrderType type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
