package com.backenEDS.dto;

import com.backenEDS.domain.enums.OrderStatus;

import jakarta.validation.constraints.NotNull;

public class StationOrderStatusUpdateDTO {

    @NotNull(message = "status es obligatorio")
    private OrderStatus status;

    // Getter y Setter
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
