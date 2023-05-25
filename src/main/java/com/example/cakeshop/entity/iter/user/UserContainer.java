package com.example.cakeshop.entity.iter.user;

import com.example.cakeshop.entity.User;
import com.example.cakeshop.entity.iter.Container;
import com.example.cakeshop.entity.iter.Iterator;

public class UserContainer implements Container<User> {

    private final User[] users;

    public UserContainer(User[] users) {
        this.users = users;
    }

    @Override
    public Iterator<User> getIterator() {
        return new UserIterator();
    }

    private class UserIterator implements Iterator<User> {

        private int index;

        private UserIterator() {
            this.index = 0;
        }

        @Override
        public boolean hasNext() {
            return index < users.length;
        }

        @Override
        public User next() {
            if(hasNext())
                return users[index++];
            return null;
        }

    }

}
