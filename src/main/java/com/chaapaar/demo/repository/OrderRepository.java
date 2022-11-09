package com.chaapaar.demo.repository;

import com.chaapaar.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByCustomer_id(Long customer_id);
}
