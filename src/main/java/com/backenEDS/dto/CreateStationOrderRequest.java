package com.backenEDS.dto;

import com.backenEDS.domain.enums.OrderType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Request body used to create a new service order.
 *
 * Business rules:
 * - status is NOT provided by the client.
 * - status is automatically initialized as CREATED.
 */
@Schema(description = "Request payload to create a new station service order")
public class CreateStationOrderRequest {

    @Schema(
        description = "Identifier of the station creating the order",
        example = "ST-001",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "stationId is mandatory")
    @Size(max = 100, message = "stationId must not exceed 100 characters")
    private String stationId;

    @Schema(
        description = "Type of the service order",
        example = "INVOICE",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "type is mandatory")
    private OrderType type;

    @Schema(
        description = "Optional description of the service order",
        example = "Invoice number 4587 requires correction"
    )
    @Size(max = 1000, message = "description must not exceed 1000 characters")
    private String description;

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
}
