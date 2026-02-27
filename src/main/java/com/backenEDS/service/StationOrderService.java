package com.backenEDS.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backenEDS.domain.StationOrder;
import com.backenEDS.domain.enums.OrderStatus;
import com.backenEDS.dto.CreateStationOrderRequest;
import com.backenEDS.dto.StationOrderResponse;
import com.backenEDS.dto.UpdateStationOrderStatusRequest;
import com.backenEDS.exception.ResourceNotFoundException;
import com.backenEDS.exception.BusinessException;
import com.backenEDS.mapper.StationOrderMapper;
import com.backenEDS.repository.StationOrderRepository;

@Service
public class StationOrderService {

    private final StationOrderRepository repository;
    private final StationOrderMapper mapper;

    public StationOrderService(StationOrderRepository repository,
                               StationOrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /**
     * Creates a new service order.
     * Initial status is always CREATED.
     */
    @Transactional
    public StationOrderResponse createOrder(CreateStationOrderRequest request) {

        StationOrder entity = mapper.toEntity(request);

        entity.setStatus(OrderStatus.CREATED);

        // Seguridad adicional por si el mapper no setea timestamps
        Instant now = Instant.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        StationOrder saved = repository.save(entity);

        return mapper.toDto(saved);
    }

    /**
     * Retrieves a service order by its UUID.
     */
    public StationOrderResponse getOrderById(UUID id) {

        StationOrder entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service order not found with id: " + id)
                );

        return mapper.toDto(entity);
    }

    /**
     * Retrieves orders filtered by stationId and/or status.
     * Applies pagination and sorting by createdAt descending.
     * Returns 404 if no results.
     */
    public Page<StationOrderResponse> getOrders(String stationId,
                                                OrderStatus status,
                                                int page,
                                                int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<StationOrder> result;

        if (stationId != null && status != null) {
            result = repository.findByStationIdAndStatus(stationId, status, pageable);
        } else if (stationId != null) {
            result = repository.findByStationId(stationId, pageable);
        } else if (status != null) {
            result = repository.findByStatus(status, pageable);
        } else {
            result = repository.findAll(pageable);
        }

        if (result.isEmpty()) {
            throw new ResourceNotFoundException(
                    "No service orders found with the specified filters"
            );
        }

        return result.map(mapper::toDto);
    }

    /**
     * Updates the status of a service order following transition rules.
     */
    @Transactional
    public StationOrderResponse updateStatus(UUID id,
                                             UpdateStationOrderStatusRequest request) {

        StationOrder entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Service order not found with id: " + id)
                );

        OrderStatus current = entity.getStatus();
        OrderStatus newStatus = request.getStatus();

        // Rule: No changes allowed if CANCELLED
        if (current == OrderStatus.CANCELLED) {
            throw new BusinessException(
                    "Cannot modify a service order that is in CANCELLED state"
            );
        }

        // Rule: DONE → IN_PROGRESS not allowed
        if (current == OrderStatus.DONE &&
                newStatus == OrderStatus.IN_PROGRESS) {

            throw new BusinessException(
                    "Invalid status transition from DONE to IN_PROGRESS"
            );
        }

        // Update status
        entity.setStatus(newStatus);

        // 🔥 CRÍTICO: siempre actualizar updatedAt
        entity.setUpdatedAt(Instant.now());

        StationOrder saved = repository.save(entity);

        return mapper.toDto(saved);
    }
}