package com.example.cakeshop.repo;

import com.example.cakeshop.entity.Inventory;
import com.example.cakeshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory, Integer>, CrudRepository<Inventory, Integer> {
    Optional<Inventory> findById(Integer id);
}
