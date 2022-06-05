package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.exception.EntityNotFoundException;
import com.doyouknowdeway.sportsequipmentrent.mapper.OrderItemMapper;
import com.doyouknowdeway.sportsequipmentrent.mapper.OrderMapper;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderItemDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderUpdateStatusDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderEntity;
import com.doyouknowdeway.sportsequipmentrent.repository.OrderItemRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderDto createOrder(final OrderCreateDto orderDto) {
        final OrderEntity orderEntity = orderMapper.orderCreateDtoToOrderEntity(orderDto);
        final OrderDto orderDtoResult = orderMapper.orderEntityToOrderDto(orderRepository.save(orderEntity));
        log.info("Order with id = {} has been created.", orderDtoResult.getId());
        return orderDtoResult;
    }

    @Override
    public void updateOrder(final OrderCreateDto orderDto, final int orderId) {
        final OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));
        orderMapper.mergeOrderEntityAndOrderCreateDto(orderEntity, orderDto);
        orderRepository.save(orderEntity);
        log.info("Order with id = {} has been updated.", orderId);
    }

    @Override
    public void updateOrderStatus(final OrderUpdateStatusDto orderDto, final int orderId) {
        final OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));
        orderMapper.mergeOrderEntityAndOrderUpdateStatusDto(orderEntity, orderDto);
        orderRepository.save(orderEntity);
        log.info("Order Status with id = {} has been updated.", orderId);
    }

    @Override
    public OrderDto getOrderById(final int orderId) {
        final Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        orderEntity.ifPresentOrElse(
                (user) -> log.info("Order with id = {} has been found.", user.getId()),
                () -> log.warn("Order with id = {} hasn't been found.", orderId));
        return orderEntity.map(orderMapper::orderEntityToOrderDto)
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));
    }

    @Override
    public void deleteOrderById(final int orderId) {
        final Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        orderEntity.ifPresentOrElse(
                (user) -> log.info("Order with id = {} has been found.", orderId),
                () -> {
                    log.warn("Order with id = {} hasn't been found.", orderId);
                    throw new EntityNotFoundException("Order not found!");
                });
        orderRepository.deleteById(orderId);
        log.info("Order with id = {} has been deleted.", orderId);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        final List<OrderEntity> orderEntities = orderRepository.findAll();
        log.info("There have been found {} orders.", orderEntities.size());
        return orderMapper.orderEntitiesToOrderDtoList(orderEntities);
    }

    @Override
    public void addItemToOrder(final int orderId, final int itemId) {
        final OrderItemDto dto = new OrderItemDto();
        dto.setOrderId(orderId);
        dto.setItemId(itemId);
        orderItemRepository.save(orderItemMapper.orderItemDtoToOrderItemEntity(dto));
        log.info("Item with id = {} has been added to order with id = {}.", itemId, orderId);
    }

}
