package com.order.application.repository;

import com.order.application.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
   // Optional<Order> findByItemName(String itemName );
    Optional <Order> findByReferenceNumber(String referenceNumber);
    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.placementTimestamp < :timestamp")
    List<Order> findOrdersByStatusAndPlacementTimestampBefore(String status, LocalDateTime timestamp);
    Page<Order> findByClientId(Integer clientId, Pageable pageable);
}
