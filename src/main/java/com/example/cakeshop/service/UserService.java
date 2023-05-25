package com.example.cakeshop.service;

import com.example.cakeshop.entity.User;
import com.example.cakeshop.entity.iter.Iterator;
import com.example.cakeshop.entity.iter.user.UserContainer;
import com.example.cakeshop.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
@SuppressWarnings("deprecation")
public class UserService implements Observer {

    @Autowired
    private UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public void deleteUserById(Integer id) {
        log.info("Deleting user with {} id", userRepo.findById(id));
        userRepo.delete(userRepo.findById(id).get());
    }

    public User fetchUserById(Integer id) throws NoSuchElementException {
        UserContainer container = new UserContainer(fetchUsers().toArray(User[]::new));
        Iterator<User> it = container.getIterator();

        while (it.hasNext()) {
            User usr = it.next();
            if (usr.getUserId() == id)
                return usr;
        }

        throw new NoSuchElementException();
    }

    public List<User> fetchUsers(){
        log.info("Fetching all users.");
        return userRepo.findAll();
    }

    public User updateUser(User user, Integer id) {
        User userDB = userRepo.findById(id).get();

        if (Objects.nonNull(user.getUsername())
                && !"".equalsIgnoreCase(user.getUsername())) {
            userDB.setUsername(user.getUsername());
        }

        if (Objects.nonNull(user.getPassword())
                && !"".equalsIgnoreCase(user.getPassword())) {
            userDB.setPassword((user.getPassword()));
        }
        userDB.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(userDB);
    }


    @Override
    public void update(Observable o, Object arg) {
        User updatedInfo = (User) arg;
        updateUser(updatedInfo, updatedInfo.getUserId());
    }
}
