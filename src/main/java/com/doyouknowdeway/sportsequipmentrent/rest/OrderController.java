package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.create_dto.OrderCreateDto;
import com.doyouknowdeway.sportsequipmentrent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    public final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody OrderCreateDto orderDto) {
        return service.createOrder(orderDto);
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@RequestBody OrderCreateDto orderDto, @PathVariable int orderId) {
        service.updateOrder(orderDto, orderId);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable int orderId) {
        return service.getOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrderById(@PathVariable int orderId) {
        service.deleteOrderById(orderId);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return service.getAllOrders();
    }

    @PostMapping("/{orderId}/items/{itemId}")
    public void addItemToOrder(@PathVariable int orderId, @PathVariable int itemId) {
        service.addItemToOrder(orderId, itemId);
    }

}
