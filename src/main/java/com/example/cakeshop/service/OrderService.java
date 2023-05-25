package com.example.cakeshop.service;

import com.example.cakeshop.entity.Cake;
import com.example.cakeshop.entity.Inventory;
import com.example.cakeshop.entity.Order;
import com.example.cakeshop.entity.User;
import com.example.cakeshop.repo.CakeRepo;
import com.example.cakeshop.repo.InventoryRepo;
import com.example.cakeshop.repo.OrderRepo;
import com.example.cakeshop.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    CakeRepo cakeRepo;

    @Autowired
    InventoryRepo inventoryRepo;

    public Optional<Order> findOrder(Integer order_id) {
        return orderRepo.findByOrderId(order_id);
    }

    public Order createOrder(Order order, Integer user_id) {

        User user = userRepo.findById(user_id).orElseThrow();

        return orderRepo.save(Order.builder()
                .user(user)
                .orderDate(order.getOrderDate())
                .deliveryDate(order.getDeliveryDate())
                .status(order.getStatus())
                .build());
    }

    public List<Cake> addCake(Integer order_id, Integer cake_id) {

        Cake cake = cakeRepo.findById(cake_id).orElseThrow();
        Order order = orderRepo.findByOrderId(order_id).orElseThrow();
        List<Inventory> inventoryList = inventoryRepo.findAll();
        Inventory foundInventory = new Inventory();
        if(!inventoryList.isEmpty()) {
            foundInventory = inventoryList.stream()
                    .filter(inventory -> inventory.getInventoryCake().getId() == cake_id)
                    .findFirst()
                    .orElse(null);
        }

        if(foundInventory.getQuantity() >= 1) {
            order.getCakes().add(cake);
            cake.getOrders().add(order);

            orderRepo.save(order);
            cakeRepo.save(cake);
            foundInventory.setQuantity(foundInventory.getQuantity() - 1);
            inventoryRepo.save(foundInventory);
        }

        return order.getCakes();

    }

    public List<Cake> deleteCake(Integer order_id, Integer cake_id) {
        Cake cake = cakeRepo.findById(cake_id).orElseThrow();
        Order order = orderRepo.findByOrderId(order_id).orElseThrow();

        List<Inventory> inventoryList = inventoryRepo.findAll();
        Inventory foundInventory = new Inventory();
        if(!inventoryList.isEmpty()) {
            foundInventory = inventoryList.stream()
                    .filter(inventory -> inventory.getInventoryCake().getId() == cake_id)
                    .findFirst()
                    .orElse(null);
        }

        order.getCakes().remove(cake);
        cake.getOrders().remove(order);
        foundInventory.setQuantity(foundInventory.getQuantity() + 1);

        inventoryRepo.save(foundInventory);
        orderRepo.save(order);
        cakeRepo.save(cake);

        return order.getCakes();
    }

    public void deleteOrder(Integer order_id) {
        orderRepo.delete(orderRepo.findByOrderId(order_id).orElseThrow());
    }

    public Order updateStatus(Integer order_id, String status) {
        Order order = orderRepo.findByOrderId(order_id).orElseThrow();
        order.setStatus(status);
        return orderRepo.save(order);
    }

    public Order updateDeliveryDate(Integer order_id, LocalDate delivery_date) {
        Order order = orderRepo.findByOrderId(order_id).orElseThrow();
        order.setDeliveryDate(delivery_date);
        return orderRepo.save(order);
    }

    public List<Order> fetchOrders() {
        return orderRepo.findAll();
    }
}
