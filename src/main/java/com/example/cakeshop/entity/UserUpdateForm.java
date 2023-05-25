package com.example.cakeshop.entity;

import lombok.*;

import java.util.Observable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuppressWarnings("deprecation")
public class UserUpdateForm extends Observable {

    private Integer id;
    private String username;
    private String password;
    private Role role;

    public User buildUser() {
        return User.builder()
                .userId(id)
                .username(username)
                .password(password)
                .role(role)
                .build();
    }

    public void setId(Integer id) {
        this.id = id;
        fireChange();
    }

    private void fireChange() {
        setChanged();
        notifyObservers(buildUser());
    }

}
