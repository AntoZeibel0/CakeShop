package com.example.cakeshop.controller;

import com.example.cakeshop.entity.User;
import com.example.cakeshop.entity.UserUpdateForm;
import com.example.cakeshop.service.UserService;
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
@RequestMapping("/user")
@EnableMethodSecurity
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok().body(userService.fetchUsers());
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?>deleteUser(@PathVariable("id") Integer id){
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("The user has been deleted.");
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUser(@RequestBody UserUpdateForm form, @PathVariable("id") Integer id) {
        form.addObserver(userService);
        form.setId(id);
        return userService.fetchUserById(id);
    }

}
