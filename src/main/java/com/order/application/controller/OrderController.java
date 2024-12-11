package com.order.application.controller;

import com.order.application.dto.OrderRequestDto;
import com.order.application.dto.OrderResponseDto;
import com.order.application.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    //place order
    @PostMapping("/place")
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @PutMapping("cancel/{referenceNumber}")
    public OrderResponseDto cancelOrder(@PathVariable String referenceNumber) {
        return orderService.cancelOrder(referenceNumber);
    }
}
