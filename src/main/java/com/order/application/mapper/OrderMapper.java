package com.order.application.mapper;

import com.order.application.dto.OrderResponseDto;
import com.order.application.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public static OrderResponseDto toOrderResponse(Order order) {
        OrderResponseDto response = new OrderResponseDto();
        response.setReferenceNumber(order.getReferenceNumber());
        response.setItemName(order.getItemName());
        response.setQuantity(order.getQuantity());
        response.setShippingAddress(order.getShippingAddress());
        response.setStatus(order.getStatus());

        return response;

    }
}
