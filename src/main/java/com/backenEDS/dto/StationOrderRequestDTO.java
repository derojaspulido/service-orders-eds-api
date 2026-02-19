package com.backenEDS.dto;

import com.backenEDS.domain.enums.OrderStatus;
import com.backenEDS.domain.enums.OrderType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StationOrderRequestDTO {

    @NotBlank(message = "stationId es obligatorio")
    private String stationId;

    @NotNull(message = "type es obligatorio")
    private OrderType type;

    @Size(max = 1000, message = "description no puede superar 1000 caracteres")
    private String description;

    @NotNull(message = "status es obligatorio")
    private OrderStatus status;

    // Getters y Setters
    public String getStationId() { return stationId; }
    public void setStationId(String stationId) { this.stationId = stationId; }

    public OrderType getType() { return type; }
    public void setType(OrderType type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
