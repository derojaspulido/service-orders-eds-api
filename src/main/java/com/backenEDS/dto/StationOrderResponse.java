package com.backenEDS.dto;

import java.time.Instant;
import java.util.UUID;

import com.backenEDS.domain.enums.OrderStatus;
import com.backenEDS.domain.enums.OrderType;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response representation of a service order.
 */
@Schema(description = "Represents a station service order")
public class StationOrderResponse {

    @Schema(description = "Unique identifier of the order", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Schema(description = "Identifier of the station that created the order", example = "ST-001")
    private String stationId;

    @Schema(description = "Type of the service order", example = "SUPPORT")
    private OrderType type;

    @Schema(description = "Optional description of the order")
    private String description;

    @Schema(description = "Current status of the order", example = "CREATED")
    private OrderStatus status;

    @Schema(description = "Timestamp when the order was created", example = "2026-02-26T18:25:43Z")
    private Instant createdAt;

    @Schema(description = "Timestamp when the order was last updated", example = "2026-02-26T18:30:00Z")
    private Instant updatedAt;

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
