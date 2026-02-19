package com.backenEDS.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backenEDS.domain.StationOrder;
import com.backenEDS.domain.enums.OrderStatus;
import com.backenEDS.dto.StationOrderRequestDTO;
import com.backenEDS.dto.StationOrderResponseDTO;
import com.backenEDS.dto.StationOrderStatusUpdateDTO;
import com.backenEDS.exception.ResourceNotFoundException;
import com.backenEDS.mapper.StationOrderMapper;
import com.backenEDS.repository.StationOrderRepository;

@Service
public class StationOrderService {

    private final StationOrderRepository repository;
    private final StationOrderMapper mapper;

    @Autowired
    public StationOrderService(StationOrderRepository repository, StationOrderMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    /** Crear nueva orden */
    @Transactional
    public StationOrderResponseDTO createOrder(StationOrderRequestDTO dto) {
        StationOrder entity = mapper.toEntity(dto);
        StationOrder saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    /** Obtener por ID */
    public StationOrderResponseDTO getOrderById(UUID id) {
        StationOrder entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada: " + id));
        return mapper.toDto(entity);
    }

    /** Filtrado con paginación y optional params */
    public Page<StationOrderResponseDTO> getOrders(String stationId, OrderStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
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
            throw new ResourceNotFoundException("No se encontraron órdenes con los filtros especificados");
        }

        return result.map(mapper::toDto);
    }

    /** Actualizar status con reglas de transición */
    @Transactional
    public StationOrderResponseDTO updateStatus(UUID id, StationOrderStatusUpdateDTO dto) {
        StationOrder entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada: " + id));

        OrderStatus current = entity.getStatus();
        OrderStatus newStatus = dto.getStatus();

        // Regla: ningún cambio si CANCELLED
        if (current == OrderStatus.CANCELLED) {
            return mapper.toDto(entity); // No cambia nada
        }

        // Regla: no permitir DONE → IN_PROGRESS
        if (current == OrderStatus.DONE && newStatus == OrderStatus.IN_PROGRESS) {
            throw new IllegalArgumentException("No se puede cambiar de DONE a IN_PROGRESS");
        }

        entity.setStatus(newStatus);
        StationOrder updated = repository.save(entity);
        return mapper.toDto(updated);
    }
}
