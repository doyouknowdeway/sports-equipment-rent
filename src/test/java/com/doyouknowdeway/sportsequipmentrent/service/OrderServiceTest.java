package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.exception.EntityNotFoundException;
import com.doyouknowdeway.sportsequipmentrent.mapper.OrderItemMapperImpl;
import com.doyouknowdeway.sportsequipmentrent.mapper.OrderMapperImpl;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderUpdateStatusDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.*;
import com.doyouknowdeway.sportsequipmentrent.repository.ItemRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.OrderItemRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.OrderRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Spy
    private OrderMapperImpl orderMapperImpl;

    @Spy
    private OrderItemMapperImpl orderItemMapperImpl;

    @Mock
    private ProfileRepository profileRepositoryMock;

    @Mock
    private OrderItemRepository orderItemRepositoryMock;

    @Mock
    private ItemRepository itemRepositoryMock;

    @Mock
    private OrderRepository orderRepositoryMock;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    private final int id = 1;
    private final int orderId = 2;
    private final int itemId = 3;
    private final ProfileEntity profileEntity = ProfileEntity.builder().id(id).build();

    private final OrderEntity orderEntity = OrderEntity.builder()
            .orderStatus(OrderStatus.NEW)
            .profile(profileEntity)
            .orderDatetime(Instant.now())
            .destinationDatetime(Instant.now())
            .expireDatetime(Instant.now())
            .build();

    private final OrderCreateDto orderCreateDto = OrderCreateDto.builder()
            .orderStatus(OrderStatus.NEW)
            .profileId(id)
            .orderDatetime(Instant.now().toString())
            .destinationDatetime(Instant.now().toString())
            .expireDatetime(Instant.now().toString())
            .build();

    private final OrderUpdateStatusDto orderUpdateStatusDto = OrderUpdateStatusDto.builder()
            .orderStatus(OrderStatus.COMPLETED)
            .build();

    private final OrderItemEntity orderItemEntity = OrderItemEntity.builder()
            .order(OrderEntity.builder().id(orderId).build())
            .item(ItemEntity.builder().id(itemId).build())
            .build();

    private final OrderDto orderDto = OrderDto.builder()
            .orderStatus(OrderStatus.NEW)
            .orderDatetime(Instant.now())
            .destinationDatetime(Instant.now())
            .expireDatetime(Instant.now())
            .build();

    @Test
    public void createOrder() {
        // given
        when(orderMapperImpl.orderCreateDtoToOrderEntity(orderCreateDto)).thenReturn(orderEntity);
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.of(profileEntity));
        when(orderRepositoryMock.save(orderEntity)).thenReturn(orderEntity);

        // when
        orderServiceImpl.createOrder(orderCreateDto);

        // then
        verify(orderRepositoryMock, times(1)).save(orderEntity);
    }

    @Test
    public void createOrderWhenUserDoesNotExist() {
        // given
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> orderServiceImpl.createOrder(orderCreateDto));
        verify(orderRepositoryMock, never()).save(orderEntity);
        assertEquals(actualException.getMessage(), "Profile not found!");
    }

    @Test
    public void updateOrder() {
        // given
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.of(orderEntity));
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.of(profileEntity));

        // when
        orderServiceImpl.updateOrder(orderCreateDto, id);

        // then
        verify(orderRepositoryMock, times(1)).save(orderEntity);
    }

    @Test
    public void updateOrderWhenOrderDoesNotExist() {
        // given
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> orderServiceImpl.updateOrder(orderCreateDto, id));
        verify(orderRepositoryMock, never()).save(orderEntity);
        assertEquals(actualException.getMessage(), "Order not found!");
    }

    @Test
    public void updateOrderWhenProfileDoesNotExist() {
        // given
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.of(orderEntity));
        when(profileRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> orderServiceImpl.updateOrder(orderCreateDto, id));
        verify(orderRepositoryMock, never()).save(orderEntity);
        assertEquals(actualException.getMessage(), "Profile not found!");
    }

    @Test
    public void updateOrderStatus() {
        // given
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.of(orderEntity));

        // when
        orderServiceImpl.updateOrderStatus(orderUpdateStatusDto, id);

        // then
        verify(orderRepositoryMock, times(1)).save(orderEntity);
    }

    @Test
    public void updateOrderStatusWhenOrderDoesNotExist() {
        // given
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> orderServiceImpl.updateOrderStatus(orderUpdateStatusDto, id));
        verify(orderRepositoryMock, never()).save(orderEntity);
        assertEquals(actualException.getMessage(), "Order not found!");
    }


    @Test
    public void getOrderById() {
        // given
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.of(orderEntity));
        when(orderMapperImpl.orderEntityToOrderDto(orderEntity)).thenReturn(orderDto);

        // when
        final OrderDto orderDtoActual = orderServiceImpl.getOrderById(id);

        // then
        verify(orderRepositoryMock, times(1)).findById(id);
        assertEquals(orderDto, orderDtoActual);
    }

    @Test
    public void getOrderByIdWhenOrderDoesNotExist() {
        // given
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> orderServiceImpl.getOrderById(id));
        verify(orderRepositoryMock, times(1)).findById(id);
        assertEquals(actualException.getMessage(), "Order not found!");
    }

    @Test
    public void deleteOrderById() {
        // given
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.of(orderEntity));

        // when
        orderServiceImpl.deleteOrderById(id);

        // then
        verify(orderRepositoryMock, times(1)).findById(id);
        verify(orderRepositoryMock, times(1)).deleteById(id);
    }

    @Test
    public void deleteOrderByIdWhenOrderDoesNotExist() {
        // given
        when(orderRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> orderServiceImpl.deleteOrderById(id));
        verify(orderRepositoryMock, times(1)).findById(id);
        assertEquals(actualException.getMessage(), "Order not found!");
    }

    @Test
    public void listOrders() {
        // given
        when(orderRepositoryMock.findAll()).thenReturn(List.of(orderEntity));
        when(orderMapperImpl.orderEntitiesToOrderDtoList(List.of(orderEntity))).thenReturn(List.of(orderDto));

        // when
        orderServiceImpl.listOrders();

        // then
        verify(orderRepositoryMock, times(1)).findAll();
    }

    @Test
    public void addItemToOrder() {
        // given
        when(orderRepositoryMock.findById(orderId)).thenReturn(
                Optional.of(OrderEntity.builder().id(orderId).build()));
        when(itemRepositoryMock.findById(itemId)).thenReturn(
                Optional.of(ItemEntity.builder().id(itemId).build()));

        // when
        orderServiceImpl.addItemToOrder(orderId, itemId);

        verify(orderRepositoryMock, times(1)).findById(orderId);
        verify(itemRepositoryMock, times(1)).findById(itemId);
    }

    @Test
    public void addItemToOrderWhenOrderDoesNotExist() {
        // given
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> orderServiceImpl.addItemToOrder(orderId, itemId));
        verify(orderRepositoryMock, times(1)).findById(orderId);
        verify(itemRepositoryMock, never()).findById(itemId);
        verify(orderItemRepositoryMock, never()).save(orderItemEntity);
        assertEquals(actualException.getMessage(), "Order not found!");
    }

    @Test
    public void addItemToOrderWhenItemDoesNotExist() {
        // given
        when(orderRepositoryMock.findById(orderId)).thenReturn(Optional.of(orderEntity));
        when(itemRepositoryMock.findById(itemId)).thenReturn(Optional.empty());

        // when then
        final Exception actualException = assertThrows(EntityNotFoundException.class,
                () -> orderServiceImpl.addItemToOrder(orderId, itemId));
        verify(orderRepositoryMock, times(1)).findById(orderId);
        verify(itemRepositoryMock, times(1)).findById(itemId);
        verify(orderItemRepositoryMock, never()).save(orderItemEntity);
        assertEquals(actualException.getMessage(), "Item not found!");
    }

}
