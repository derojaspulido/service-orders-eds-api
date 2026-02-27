package com.backenEDS.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.backenEDS.domain.StationOrder;
import com.backenEDS.dto.CreateStationOrderRequest;
import com.backenEDS.dto.StationOrderResponse;

/**
 * Mapper responsible for converting between
 * StationOrder entity and DTOs.
 */
@Mapper(componentModel = "spring")
public interface StationOrderMapper {

    /**
     * Converts CreateStationOrderRequest to StationOrder entity.
     * id, createdAt and updatedAt are managed automatically.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StationOrder toEntity(CreateStationOrderRequest request);

    /**
     * Converts StationOrder entity to response DTO.
     */
    StationOrderResponse toDto(StationOrder entity);
}
