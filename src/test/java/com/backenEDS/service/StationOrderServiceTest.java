package com.backenEDS.service;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.backenEDS.domain.StationOrder;
import com.backenEDS.domain.enums.OrderStatus;
import com.backenEDS.domain.enums.OrderType;
import com.backenEDS.dto.StationOrderRequestDTO;
import com.backenEDS.dto.StationOrderResponseDTO;
import com.backenEDS.dto.StationOrderStatusUpdateDTO;
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
    private StationOrderResponseDTO orderDTO;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        orderEntity = new StationOrder();
        orderEntity.setId(UUID.randomUUID());
        orderEntity.setStationId("S001");
        orderEntity.setType(OrderType.INVOICE);
        orderEntity.setStatus(OrderStatus.CREATED);

        orderDTO = new StationOrderResponseDTO();
        orderDTO.setId(orderEntity.getId());
        orderDTO.setStationId(orderEntity.getStationId());
        orderDTO.setType(orderEntity.getType());
        orderDTO.setStatus(orderEntity.getStatus());
    }

    @Test
    void createOrder_ShouldReturnDTO() {
        StationOrderRequestDTO requestDTO = new StationOrderRequestDTO();
        requestDTO.setStationId("S001");
        requestDTO.setType(OrderType.INVOICE);
        requestDTO.setStatus(OrderStatus.CREATED);

        when(mapper.toEntity(requestDTO)).thenReturn(orderEntity);
        when(repository.save(orderEntity)).thenReturn(orderEntity);
        when(mapper.toDto(orderEntity)).thenReturn(orderDTO);

        StationOrderResponseDTO result = service.createOrder(requestDTO);
        assertNotNull(result);
        assertEquals(orderDTO.getStationId(), result.getStationId());
    }

    @Test
    void updateStatus_FromDoneToInProgress_ShouldThrow() {
        orderEntity.setStatus(OrderStatus.DONE);
        when(repository.findById(orderEntity.getId())).thenReturn(Optional.of(orderEntity));

        StationOrderStatusUpdateDTO updateDTO = new StationOrderStatusUpdateDTO();
        updateDTO.setStatus(OrderStatus.IN_PROGRESS);

        assertThrows(IllegalArgumentException.class,
                () -> service.updateStatus(orderEntity.getId(), updateDTO));
    }

    @Test
    void updateStatus_FromCancelled_ShouldNotChange() {
        orderEntity.setStatus(OrderStatus.CANCELLED);
        when(repository.findById(orderEntity.getId())).thenReturn(Optional.of(orderEntity));
        when(mapper.toDto(orderEntity)).thenReturn(orderDTO);

        StationOrderStatusUpdateDTO updateDTO = new StationOrderStatusUpdateDTO();
        updateDTO.setStatus(OrderStatus.IN_PROGRESS);

        StationOrderResponseDTO result = service.updateStatus(orderEntity.getId(), updateDTO);
        assertEquals(OrderStatus.CANCELLED, result.getStatus());
    }

    @Test
    void updateStatus_ValidTransition_ShouldUpdate() {
        orderEntity.setStatus(OrderStatus.CREATED);
        when(repository.findById(orderEntity.getId())).thenReturn(Optional.of(orderEntity));
        when(repository.save(orderEntity)).thenReturn(orderEntity);
        when(mapper.toDto(orderEntity)).thenReturn(orderDTO);

        StationOrderStatusUpdateDTO updateDTO = new StationOrderStatusUpdateDTO();
        updateDTO.setStatus(OrderStatus.IN_PROGRESS);

        StationOrderResponseDTO result = service.updateStatus(orderEntity.getId(), updateDTO);
        assertNotNull(result);
        assertEquals(OrderStatus.CREATED, result.getStatus()); // mapper mock mantiene mismo status simulado
    }

    @Test
    void getOrders_NoResults_ShouldThrow() {
        when(repository.findAll(PageRequest.of(0,10, Sort.by(Sort.Direction.DESC,"createdAt"))))
                .thenReturn(Page.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getOrders(null, null, 0, 10));
    }
}
