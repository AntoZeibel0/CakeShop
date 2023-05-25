package com.example.cakeshop.service;

import com.example.cakeshop.entity.Cake;
import com.example.cakeshop.entity.Inventory;
import com.example.cakeshop.repo.CakeRepo;
import com.example.cakeshop.repo.InventoryRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class InventoryService {

    @Autowired
    InventoryRepo inventoryRepo;

    @Autowired
    CakeRepo cakeRepo;

    public Inventory createInventoryItem(Inventory inventory, Integer cakeId){
        Cake cake = cakeRepo.findById(cakeId).orElseThrow();
        return inventoryRepo.save(Inventory.builder()
                .inventoryCake(cake)
                .availability(inventory.isAvailability())
                .quantity(inventory.getQuantity())
                .build());
    }

    public List<Inventory> fetchInventoryItems() {
        return (List<Inventory>)inventoryRepo.findAll();
    }

    public void deleteInventoryItemById(Integer id) {
        inventoryRepo.delete(inventoryRepo.findById(id).orElseThrow());
    }

    public Inventory updateInventory(Inventory inventory, Integer id, Integer cake_id) {
        Inventory inventoryDB = inventoryRepo.findById(id).orElseThrow();
        Cake cake = cakeRepo.findById(cake_id).orElseThrow();
        inventoryDB.setAvailability(inventory.isAvailability());
        inventoryDB.setQuantity(inventory.getQuantity());
        inventoryDB.setInventoryCake(cake);
        return inventoryRepo.save(inventoryDB);
    }

}
