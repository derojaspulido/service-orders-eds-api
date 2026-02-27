package com.backenEDS.service;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.backenEDS.domain.StationOrder;
import com.backenEDS.domain.enums.OrderStatus;
import com.backenEDS.domain.enums.OrderType;
import com.backenEDS.dto.CreateStationOrderRequest;
import com.backenEDS.dto.StationOrderResponse;
import com.backenEDS.dto.UpdateStationOrderStatusRequest;
import com.backenEDS.exception.BusinessException;
import com.backenEDS.exception.ResourceNotFoundException;
import com.backenEDS.mapper.StationOrderMapper;
import com.backenEDS.repository.StationOrderRepository;

class StationOrderServiceTest {

    @Mock
    private StationOrderRepository repository;

    @Mock
    private StationOrderMapper mapper;

    @InjectMocks
    private StationOrderService service;

    private StationOrder orderEntity;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        orderEntity = new StationOrder();
        orderEntity.setId(UUID.randomUUID());
        orderEntity.setStationId("S001");
        orderEntity.setType(OrderType.INVOICE);
        orderEntity.setStatus(OrderStatus.CREATED);
    }

    @Test
    void createOrder_ShouldReturnDTO() {

        CreateStationOrderRequest requestDTO = new CreateStationOrderRequest();
        requestDTO.setStationId("S001");
        requestDTO.setType(OrderType.INVOICE);

        when(mapper.toEntity(requestDTO)).thenReturn(orderEntity);
        when(repository.save(orderEntity)).thenReturn(orderEntity);
        when(mapper.toDto(orderEntity)).thenAnswer(inv -> {
            StationOrder entity = inv.getArgument(0);
            StationOrderResponse dto = new StationOrderResponse();
            dto.setId(entity.getId());
            dto.setStationId(entity.getStationId());
            dto.setType(entity.getType());
            dto.setStatus(entity.getStatus());
            return dto;
        });

        StationOrderResponse result = service.createOrder(requestDTO);

        assertNotNull(result);
        assertEquals("S001", result.getStationId());
    }

    @Test
    void updateStatus_FromDoneToInProgress_ShouldThrow() {

        orderEntity.setStatus(OrderStatus.DONE);
        when(repository.findById(orderEntity.getId()))
                .thenReturn(Optional.of(orderEntity));

        UpdateStationOrderStatusRequest updateDTO
                = new UpdateStationOrderStatusRequest();
        updateDTO.setStatus(OrderStatus.IN_PROGRESS);

        assertThrows(BusinessException.class,
                () -> service.updateStatus(orderEntity.getId(), updateDTO));
    }

    @Test
    void updateStatus_FromCancelled_ShouldThrow() {

        orderEntity.setStatus(OrderStatus.CANCELLED);

        when(repository.findById(orderEntity.getId()))
                .thenReturn(Optional.of(orderEntity));

        UpdateStationOrderStatusRequest updateDTO
                = new UpdateStationOrderStatusRequest();
        updateDTO.setStatus(OrderStatus.IN_PROGRESS);

        assertThrows(BusinessException.class,
                () -> service.updateStatus(orderEntity.getId(), updateDTO));
    }

    @Test
    void updateStatus_ValidTransition_ShouldUpdate() {

        orderEntity.setStatus(OrderStatus.CREATED);

        when(repository.findById(orderEntity.getId()))
                .thenReturn(Optional.of(orderEntity));

        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        when(mapper.toDto(any())).thenAnswer(inv -> {
            StationOrder entity = inv.getArgument(0);
            StationOrderResponse dto = new StationOrderResponse();
            dto.setStatus(entity.getStatus());
            return dto;
        });

        UpdateStationOrderStatusRequest updateDTO
                = new UpdateStationOrderStatusRequest();
        updateDTO.setStatus(OrderStatus.IN_PROGRESS);

        StationOrderResponse result
                = service.updateStatus(orderEntity.getId(), updateDTO);

        assertEquals(OrderStatus.IN_PROGRESS, result.getStatus());
    }

    @Test
    void getOrders_NoResults_ShouldThrow() {

        when(repository.findAll(any(PageRequest.class)))
                .thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getOrders(null, null, 0, 10));
    }
}
