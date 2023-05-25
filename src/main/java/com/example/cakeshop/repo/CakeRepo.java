package com.example.cakeshop.repo;

import com.example.cakeshop.entity.Cake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CakeRepo extends JpaRepository<Cake, Integer>, CrudRepository<Cake, Integer> {
    Optional<Cake> findCakeById(Integer id);
}
