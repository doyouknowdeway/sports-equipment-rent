package com.doyouknowdeway.sportsequipmentrent.service;

import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.OrderCreateDto;

import java.util.List;

public interface OrderService {

    OrderDto createOrder(OrderCreateDto orderDto);

    void updateOrder(OrderCreateDto orderDto, int orderId);

    OrderDto getOrderById(int orderId);

    void deleteOrderById(int orderId);

    List<OrderDto> getAllOrders();

    void addItemToOrder(int orderId, int itemId);

}
