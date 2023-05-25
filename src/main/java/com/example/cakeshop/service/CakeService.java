package com.example.cakeshop.service;

import com.example.cakeshop.entity.Cake;
import com.example.cakeshop.repo.CakeRepo;
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
public class CakeService {

    @Autowired
    CakeRepo cakeRepo;

    public Cake createCake(Cake cake) {

        return cakeRepo.save(cake);
    }

    public List<Cake> fetchCakes(){
        return cakeRepo.findAll();
    }

    public void deleteCakeById(Integer id) {
        cakeRepo.delete(cakeRepo.findById(id).get());
    }

    public Cake updateCake(Cake cake, Integer id) {
        Cake cakeDB = cakeRepo.findCakeById(id).get();

        if(Objects.nonNull(cake.getName()) && !"".equalsIgnoreCase(cake.getName())) {
            cakeDB.setName(cake.getName());
        }

        if(Objects.nonNull(cake.getDescription()) && !"".equalsIgnoreCase(cake.getDescription())) {
            cakeDB.setDescription(cake.getDescription());
        }

        if(Objects.nonNull(cake.getPrice())) {
            cakeDB.setPrice(cake.getPrice());
        }

        return cakeRepo.save(cakeDB);

    }

}
