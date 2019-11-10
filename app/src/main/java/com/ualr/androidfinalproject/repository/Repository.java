package com.ualr.androidfinalproject.repository;

import android.util.Log;

import com.ualr.androidfinalproject.model.User;
import com.ualr.androidfinalproject.model.UserViewModel;
import com.ualr.androidfinalproject.network.WebAPI;
import com.ualr.androidfinalproject.network.WebServiceManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static final String TAG = "FinalProject";
    private WebAPI userAPI;
    private UserViewModel userViewModel;

    public Repository(UserViewModel userViewModel) {
        userAPI = WebServiceManager.getService();
        this.userViewModel = userViewModel;
    }

    public void getUserList() {
        userAPI.getUserList().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NotNull Call<List<User>> call, @NotNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    userViewModel.setUsers(response.body());
                    Log.d(TAG, "Successfully Retrieved the List of " + userViewModel.getUsers().size() + " Users into the User View Model!");
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<User>> call, @NotNull Throwable t) {
                Log.e(TAG, call.request().headers().toString());
            }
        });
    }

    public void register(final User user) {
        userAPI.register(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    userViewModel.addUser(response.body());
                    Log.d(TAG, "Successfully Registered a New User Account with Email: " + user.getEmail() + " and Password: " + user.getPassword() + "!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, call.request().headers().toString());
            }
        });
    }

}
