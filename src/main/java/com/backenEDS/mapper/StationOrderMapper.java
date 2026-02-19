package com.backenEDS.mapper;

import com.backenEDS.domain.StationOrder;
import com.backenEDS.dto.StationOrderRequestDTO;
import com.backenEDS.dto.StationOrderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StationOrderMapper {

    StationOrderMapper INSTANCE = Mappers.getMapper(StationOrderMapper.class);

    // DTO → Entity
    StationOrder toEntity(StationOrderRequestDTO dto);

    // Entity → DTO
    StationOrderResponseDTO toDto(StationOrder entity);

    // Actualizar status solo en Entity
    void updateStatus(@MappingTarget StationOrder entity, StationOrderRequestDTO dto);
}
