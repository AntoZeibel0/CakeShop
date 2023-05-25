package com.example.cakeshop.controller;

import com.example.cakeshop.entity.Inventory;
import com.example.cakeshop.service.InventoryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inventory")
@EnableMethodSecurity
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @GetMapping("/getInventoryItem")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Inventory>> getInventory() {
        return ResponseEntity.ok().body(inventoryService.fetchInventoryItems());
    }

    @PostMapping("/createInventoryItem/{cake_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Inventory createInventoryItem(@RequestBody Inventory inventory, @PathVariable("cake_id") Integer cake_id) {
        return inventoryService.createInventoryItem(inventory, cake_id);
    }

    @DeleteMapping("/deleteInventoryItem/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>deleteInventoryItem(@PathVariable("id") Integer id) {
        inventoryService.deleteInventoryItemById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateInventoryItem/{inv_id}/{cake_id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Inventory updateInventoryItem(@RequestBody Inventory inventory, @PathVariable("inv_id") Integer inv_id, @PathVariable("cake_id") Integer cake_id) {
        return inventoryService.updateInventory(inventory, inv_id, cake_id);
    }
}
