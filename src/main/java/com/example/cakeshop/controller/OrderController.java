package com.example.cakeshop.controller;

import com.example.cakeshop.entity.Order;
import com.example.cakeshop.entity.User;
import com.example.cakeshop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@EnableMethodSecurity
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/createOrder")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public Order createOrder(@RequestBody Order order, Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        return orderService.createOrder(order, principal.getUserId());
    }

    @PostMapping("/addCakeToOrder/{order_id}/{cake_id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> addCake(@PathVariable("order_id") Integer order_id,
                              @PathVariable("cake_id") Integer cake_id,
                              Authentication authentication)  {
        User principal = (User) authentication.getPrincipal();
        Order order = orderService.findOrder(order_id).get();
        if(principal.getUserId() != order.getUser().getUserId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The order does not belong to the user");
        }
        return ResponseEntity.ok(orderService.addCake(order_id, cake_id));
    }

    @DeleteMapping("/deleteCakeFromOrder/{order_id}/{cake_id}")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    public ResponseEntity<?> deleteCake(@PathVariable("order_id") Integer order_id,
                                        @PathVariable("cake_id") Integer cake_id,
                                        Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        Order order = orderService.findOrder(order_id).get();
        if(principal.getUserId() != order.getUser().getUserId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("The order does not belong to the user.");
        }
        return ResponseEntity.ok(orderService.deleteCake(order_id, cake_id));
    }

    @DeleteMapping("/deleteOrder/{order_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteOrder(@PathVariable("order_id") Integer order_id) {
        orderService.deleteOrder(order_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("The order has been deleted.");
    }

    @PutMapping("/updateStatus/{order_id}/{status}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateStatus(@PathVariable("order_id") Integer order_id,
                                          @PathVariable("status") String status) {
        orderService.updateStatus(order_id,status);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("The status has been changed.");
    }

    @PutMapping("/updateDeliveryDate/{order_id}/{delivery_date}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateStatus(@PathVariable("order_id") Integer order_id,
                                          @PathVariable("delivery_date") LocalDate delivery_date) {
        orderService.updateDeliveryDate(order_id,delivery_date);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("The delivery date has been changed.");
    }

    @GetMapping("/getOrders")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok().body(orderService.fetchOrders());
    }

}
