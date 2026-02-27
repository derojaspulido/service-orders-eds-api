package com.backenEDS.dto;

import com.backenEDS.domain.enums.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Request body used to update the status of a service order.
 */
@Schema(description = "Request payload to update the status of a service order")
public class UpdateStationOrderStatusRequest {

    @Schema(
        description = "New status to assign to the service order",
        example = "IN_PROGRESS",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "status is mandatory")
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
