package com.dmtroncoso.satapp.retrofit.service;

import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.model.UserResponse;
import com.dmtroncoso.satapp.retrofit.model.register.UserRegister;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SataService {

    @POST("/auth")
    Call<UserResponse> login();

    @POST("/users")
    Call<User> register(@Body UserRegister userRegister);
}
