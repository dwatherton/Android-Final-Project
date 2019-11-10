package com.ualr.androidfinalproject.network;

import com.ualr.androidfinalproject.model.User;
import com.ualr.androidfinalproject.network.session.SessionDataQuery;
import com.ualr.androidfinalproject.network.session.SessionDataResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebAPI {

    @GET("ds/shoppers")
    Call<List<User>> getUserList();

    @POST("ds/shoppers")
    Call<User> register(@Body User user);

    @POST("auth/refresh")
    Call<SessionDataResponse> refreshAccessToken(@Body SessionDataQuery refreshToken);

}