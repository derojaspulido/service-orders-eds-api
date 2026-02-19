package com.backenEDS.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.backenEDS.domain.StationOrder;
import com.backenEDS.domain.enums.OrderStatus;

public interface StationOrderRepository extends JpaRepository<StationOrder, UUID> {

    Optional<StationOrder> findById(UUID id);

    // Filtrado por stationId y/o status con paginación
    Page<StationOrder> findByStationIdAndStatus(String stationId, OrderStatus status, Pageable pageable);

    Page<StationOrder> findByStationId(String stationId, Pageable pageable);

    Page<StationOrder> findByStatus(OrderStatus status, Pageable pageable);

}
