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
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderMapper orderMapper,
                            OrderItemRepository orderItemRepository,
                            OrderItemMapper orderItemMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderDto createOrder(OrderCreateDto orderDto) {
        return orderMapper.fromEntity(orderRepository.save(orderMapper.toEntity(orderDto)));
    }

    @Override
    public void updateOrder(OrderCreateDto orderDto, int orderId) {
        OrderEntity orderEntity = orderRepository.getById(orderId);
        orderMapper.merge(orderDto, orderEntity);
        orderRepository.save(orderEntity);
    }

    @Override
    public OrderDto getOrderById(int orderId) {
        return orderMapper.fromEntity(orderRepository.findById(orderId).orElseThrow());
    }

    @Override
    public void deleteOrderById(int orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderMapper.fromEntities(orderRepository.findAll());
    }

    @Override
    public void addItemToOrder(int orderId, int itemId) {
        OrderItemDto dto = new OrderItemDto();
        dto.setOrderId(orderId);
        dto.setItemId(itemId);

        orderItemRepository.save(orderItemMapper.toEntity(dto));
    }

}
