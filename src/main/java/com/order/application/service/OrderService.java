package com.order.application.service;

import com.order.application.dto.OrderRequestDto;
import com.order.application.dto.OrderResponseDto;
import com.order.application.entity.Client;
import com.order.application.entity.Inventory;
import com.order.application.entity.Order;
import com.order.application.entity.OrderStatus;
import com.order.application.mapper.OrderMapper;
import com.order.application.repository.ClientRepo;
import com.order.application.repository.InventoryRepo;
import com.order.application.repository.OrderRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final InventoryRepo inventoryRepo;
    private final ClientRepo clientRepo;
    public OrderService(OrderRepo orderRepo, InventoryRepo inventoryRepo,ClientRepo clientRepo) {
        this.orderRepo = orderRepo;
        this.inventoryRepo = inventoryRepo;
        this.clientRepo = clientRepo;
    }

    // place order
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {

        // Get authenticated user's email
        String clientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (clientEmail == null || clientEmail.isEmpty()) {
            throw new RuntimeException("Client is not authenticated");
        }
        Client client = clientRepo.findByEmail(clientEmail)
                .orElseThrow(() -> new RuntimeException("Client not found"));


        Optional<Inventory> inventoryOpt = inventoryRepo.findByProductName(orderRequestDto.getItemName());

        if(inventoryOpt.isEmpty() || inventoryOpt.get().getQuantity() < orderRequestDto.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }
        Inventory inventory = inventoryOpt.get();
        inventory.setQuantity(inventory.getQuantity() - orderRequestDto.getQuantity());
        inventoryRepo.save(inventory);

        //Save order
        Order order = new Order();
        order.setItemName(orderRequestDto.getItemName());
        order.setQuantity(orderRequestDto.getQuantity());
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setStatus("NEW");
        order.setPlacementTimestamp(LocalDateTime.now());
        order.setClient(client);
        orderRepo.save(order);
        return OrderMapper.toOrderResponse(order);
    }
    // cancel order
    @Transactional
    public OrderResponseDto cancelOrder(String referenceNumber) {
        Optional<Order> orderOpt = orderRepo.findByReferenceNumber(referenceNumber);
        if(orderOpt.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        Order order = orderOpt.get();
        if(!order.getStatus().equals("NEW")) {
            throw new RuntimeException("Only NEW orders can be canceled.");
        }
        //Restock
        Inventory inventory = inventoryRepo.findByProductName(order.getItemName()).orElseThrow(() -> new RuntimeException("Inventory not found"));

        inventory.setQuantity(inventory.getQuantity() + order.getQuantity());
        inventoryRepo.save(inventory);

        order.setStatus("CANCELLED");
        orderRepo.save(order);
        return OrderMapper.toOrderResponse(order);
    }
}
