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
    public OrderController(final OrderService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody final OrderCreateDto orderDto) {
        return service.createOrder(orderDto);
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@RequestBody final OrderCreateDto orderDto, @PathVariable final int orderId) {
        service.updateOrder(orderDto, orderId);
    }

    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable final int orderId) {
        return service.getOrderById(orderId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrderById(@PathVariable final int orderId) {
        service.deleteOrderById(orderId);
    }

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return service.getAllOrders();
    }

    @PostMapping("/{orderId}/items/{itemId}")
    public void addItemToOrder(@PathVariable final int orderId, @PathVariable final int itemId) {
        service.addItemToOrder(orderId, itemId);
    }

}
