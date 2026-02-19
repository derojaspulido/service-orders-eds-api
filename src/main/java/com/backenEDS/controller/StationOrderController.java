package com.backenEDS.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backenEDS.domain.enums.OrderStatus;
import com.backenEDS.dto.StationOrderRequestDTO;
import com.backenEDS.dto.StationOrderResponseDTO;
import com.backenEDS.dto.StationOrderStatusUpdateDTO;
import com.backenEDS.service.StationOrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/service-orders")
public class StationOrderController {

    private final StationOrderService service;

    @Autowired
    public StationOrderController(StationOrderService service) {
        this.service = service;
    }

    /** POST /service-orders → crear nueva orden */
    @PostMapping
    public ResponseEntity<StationOrderResponseDTO> createOrder(
            @Valid @RequestBody StationOrderRequestDTO dto) {
        StationOrderResponseDTO response = service.createOrder(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /** GET /service-orders/{id} → obtener orden por ID */
    @GetMapping("/{id}")
    public ResponseEntity<StationOrderResponseDTO> getOrderById(@PathVariable UUID id) {
        StationOrderResponseDTO response = service.getOrderById(id);
        return ResponseEntity.ok(response);
    }

    /** GET /service-orders?stationId=&status=&page=&size → filtrado con paginación */
    @GetMapping
    public ResponseEntity<Page<StationOrderResponseDTO>> getOrders(
            @RequestParam(required = false) String stationId,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<StationOrderResponseDTO> response = service.getOrders(stationId, status, page, size);
        return ResponseEntity.ok(response);
    }

    /** PATCH /service-orders/{id}/status → actualizar status */
    @PatchMapping("/{id}/status")
    public ResponseEntity<StationOrderResponseDTO> updateStatus(
            @PathVariable UUID id,
            @Valid @RequestBody StationOrderStatusUpdateDTO dto
    ) {
        StationOrderResponseDTO response = service.updateStatus(id, dto);
        return ResponseEntity.ok(response);
    }
}
