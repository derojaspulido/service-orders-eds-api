package com.backenEDS.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backenEDS.domain.enums.OrderStatus;
import com.backenEDS.dto.CreateStationOrderRequest;
import com.backenEDS.dto.StationOrderResponse;
import com.backenEDS.dto.UpdateStationOrderStatusRequest;
import com.backenEDS.service.StationOrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST controller for managing service orders.
 */
@RestController
@RequestMapping("/service-orders")
@Tag(name = "Service Orders", description = "Operations related to station service orders")
public class StationOrderController {

    private final StationOrderService service;

    public StationOrderController(StationOrderService service) {
        this.service = service;
    }

    /**
     * POST /service-orders
     * Creates a new service order.
     * Returns 400 if validation fails.
     */
    @Operation(summary = "Create a new service order")
    @PostMapping
    public ResponseEntity<StationOrderResponse> createOrder(
            @Valid @RequestBody CreateStationOrderRequest request) {

        StationOrderResponse response = service.createOrder(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /service-orders/{id}
     * Returns 404 if order does not exist.
     */
    @Operation(summary = "Get a service order by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<StationOrderResponse> getOrderById(
            @Parameter(description = "UUID of the service order")
            @PathVariable UUID id) {

        return ResponseEntity.ok(service.getOrderById(id));
    }

    /**
     * GET /service-orders?stationId=&status=&page=&size=
     *
     * Pagination is mandatory.
     * Sorted by createdAt DESC.
     * Returns 404 if no results.
     */
    @Operation(summary = "Filter service orders with pagination")
    @GetMapping
    public ResponseEntity<Page<StationOrderResponse>> getOrders(

            @Parameter(description = "Filter by station identifier")
            @RequestParam(required = false) String stationId,

            @Parameter(description = "Filter by order status")
            @RequestParam(required = false) OrderStatus status,

            @Parameter(description = "Page number (default 0)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Page size (default 10)")
            @RequestParam(defaultValue = "10") int size
    ) {

        Page<StationOrderResponse> result =
                service.getOrders(stationId, status, page, size);

        return ResponseEntity.ok(result);
    }

    /**
     * PATCH /service-orders/{id}/status
     *
     * Enforces transition rules:
     * - No change if CANCELLED
     * - DONE → IN_PROGRESS not allowed
     * Updates updatedAt
     */
    @Operation(summary = "Update the status of a service order")
    @PatchMapping("/{id}/status")
    public ResponseEntity<StationOrderResponse> updateStatus(

            @Parameter(description = "UUID of the service order")
            @PathVariable UUID id,

            @Valid @RequestBody UpdateStationOrderStatusRequest request
    ) {

        StationOrderResponse response =
                service.updateStatus(id, request);

        return ResponseEntity.ok(response);
    }
}