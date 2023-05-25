package com.example.cakeshop.controller;

import com.example.cakeshop.entity.Cake;
import com.example.cakeshop.service.CakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cakes")
@EnableMethodSecurity
public class CakeController {
    @Autowired
    CakeService cakeService;

    @GetMapping("/getCakes")
    public ResponseEntity<List<Cake>> getCakes(){
        return ResponseEntity.ok().body(cakeService.fetchCakes());
    }

    @PostMapping("/createCake")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cake createCake(@RequestBody Cake Cake) {
        return cakeService.createCake(Cake);
    }

    @DeleteMapping("/deleteCake/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>deleteCake(@PathVariable("id") Integer id){
        cakeService.deleteCakeById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("The cake has been deleted.");
    }

    @PutMapping("/updateCake/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cake updateCake(@RequestBody Cake cake , @PathVariable("id") Integer id){
        ResponseEntity.status(HttpStatus.ACCEPTED).body("The cake has been updated.");
        return cakeService.updateCake(cake,id);
    }
}
