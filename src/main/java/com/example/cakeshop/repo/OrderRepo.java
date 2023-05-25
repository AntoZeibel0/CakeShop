package com.example.cakeshop.repo;

import com.example.cakeshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer>, CrudRepository<Order, Integer> {
    Optional<Order> findByOrderId(Integer id);
}
