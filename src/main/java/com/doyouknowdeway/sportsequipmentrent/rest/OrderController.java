package com.doyouknowdeway.sportsequipmentrent.rest;

import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderCreateDto;
import com.doyouknowdeway.sportsequipmentrent.model.dto.OrderUpdateStatusDto;
import com.doyouknowdeway.sportsequipmentrent.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    public final OrderService orderService;

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody @Valid final OrderCreateDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{orderId}")
    public void updateOrder(@RequestBody @Valid final OrderCreateDto orderDto, @PathVariable final int orderId) {
        orderService.updateOrder(orderDto, orderId);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{orderId}/status")
    public void updateOrderStatus(@RequestBody @Valid final OrderUpdateStatusDto orderDto, @PathVariable final int orderId) {
        orderService.updateOrderStatus(orderDto, orderId);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{orderId}")
    public OrderDto getOrderById(@PathVariable final int orderId) {
        return orderService.getOrderById(orderId);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @DeleteMapping("/{orderId}")
    public void deleteOrderById(@PathVariable final int orderId) {
        orderService.deleteOrderById(orderId);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.listOrders();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping("/{orderId}/items/{itemId}")
    public void addItemToOrder(@PathVariable final int orderId, @PathVariable final int itemId) {
        orderService.addItemToOrder(orderId, itemId);
    }

}
