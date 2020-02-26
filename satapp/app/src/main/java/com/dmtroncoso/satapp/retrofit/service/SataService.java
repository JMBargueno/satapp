package com.dmtroncoso.satapp.retrofit.service;

import com.dmtroncoso.satapp.retrofit.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.POST;

public interface SataService {

    @POST("/auth")
    Call<UserResponse> login();
}
