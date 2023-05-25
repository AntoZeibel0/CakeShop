package com.example.cakeshop.repo;

import com.example.cakeshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>, CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
