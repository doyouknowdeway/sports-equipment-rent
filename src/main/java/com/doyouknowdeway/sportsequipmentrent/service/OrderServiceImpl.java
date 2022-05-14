package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.mapper.OrderItemMapper;
import com.doyouknowdeway.sportsequipmentrent.mapper.OrderMapper;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderItemDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.OrderCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.entity.OrderEntity;
import com.doyouknowdeway.sportsequipmentrent.repository.OrderItemRepository;
import com.doyouknowdeway.sportsequipmentrent.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Autowired
    public OrderServiceImpl(final OrderRepository orderRepository,
                            final OrderMapper orderMapper,
                            final OrderItemRepository orderItemRepository,
                            final OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderDto createOrder(final OrderCreateDto orderDto) {
        return orderMapper.fromEntity(orderRepository.save(orderMapper.toEntity(orderDto)));
    }

    @Override
    public void updateOrder(final OrderCreateDto orderDto, final int orderId) {
        final OrderEntity orderEntity = orderRepository.getOne(orderId);
        orderMapper.merge(orderEntity, orderDto);
        orderRepository.save(orderEntity);
    }

    @Override
    public OrderDto getOrderById(final int orderId) {
        return orderMapper.fromEntity(orderRepository.findById(orderId).orElseThrow());
    }

    @Override
    public void deleteOrderById(final int orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderMapper.fromEntities(orderRepository.findAll());
    }

    @Override
    public void addItemToOrder(final int orderId, final int itemId) {
        final OrderItemDto dto = new OrderItemDto();
        dto.setOrderId(orderId);
        dto.setItemId(itemId);

        orderItemRepository.save(orderItemMapper.toEntity(dto));
    }

}
