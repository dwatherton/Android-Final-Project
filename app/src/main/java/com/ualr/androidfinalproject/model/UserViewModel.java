package com.ualr.androidfinalproject.model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.SortedList;

import java.util.List;
import java.util.Objects;

public class UserViewModel extends ViewModel {

    private MutableLiveData<SortedList<Item>> items;
    private MutableLiveData<List<User>> users;
    private User user;

    public UserViewModel() {
        this.items = new MutableLiveData<>();
        this.users = new MutableLiveData<>();
    }

    public SortedList<Item> getItems() {
        return this.items.getValue();
    }

    public void setItems(SortedList<Item> items) {
        this.items.setValue(items);
    }

    public List<User> getUsers() {
        return this.users.getValue();
    }

    public void setUsers(List<User> users) {
        this.users.setValue(users);
    }

    public User getUser(String email) {
        if (users.getValue() == null || users.getValue().isEmpty())
        {
            return null;
        }
        this.user = null;
        for (User u : Objects.requireNonNull(users.getValue())) {
            if (u.getEmail().equals(email))
            {
                this.user = u;
                break;
            }
        }
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addUser(User user) {
        List<User> userList = users.getValue();
        userList.add(user);
        setUsers(userList);
        setUser(user);
    }

}
