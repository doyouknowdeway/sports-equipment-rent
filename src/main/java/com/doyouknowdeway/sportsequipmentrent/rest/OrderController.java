package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderUpdateStatusDto;
import com.doyouknowdeway.sportsequipmentrent.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    public final OrderService orderService;

    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody final OrderCreateDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@RequestBody final OrderCreateDto orderDto, @PathVariable final int orderId) {
        orderService.updateOrder(orderDto, orderId);
    }

    @PutMapping("/{orderId}/status")
    public void updateOrderStatus(@RequestBody final OrderUpdateStatusDto orderDto, @PathVariable final int orderId) {
        orderService.updateOrderStatus(orderDto, orderId);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable final int orderId) {
        return orderService.getOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrderById(@PathVariable final int orderId) {
        orderService.deleteOrderById(orderId);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/{orderId}/items/{itemId}")
    public void addItemToOrder(@PathVariable final int orderId, @PathVariable final int itemId) {
        orderService.addItemToOrder(orderId, itemId);
    }

}
