package com.order.application.service;

import com.order.application.entity.Order;
import com.order.application.repository.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderScheduler {
    private final OrderRepo orderRepo;

    public OrderScheduler(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }
    @Scheduled(fixedRate = 300000)
    @Transactional
    public void dispatchNewOrders(){
        try {
            List<Order> newOrders = orderRepo.findOrdersByStatusAndPlacementTimestampBefore(
                    "NEW", LocalDateTime.now().minusMinutes(5)
            );

            for (Order order : newOrders) {
                order.setStatus("DISPATCHED");
            }

            orderRepo.saveAll(newOrders);
        } catch (Exception e) {
            // Log the error and continue the scheduler
            System.err.println("Error dispatching orders: " + e.getMessage());
        }
    }
}
