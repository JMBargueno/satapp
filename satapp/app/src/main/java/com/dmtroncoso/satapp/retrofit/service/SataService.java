package com.dmtroncoso.satapp.retrofit.service;

import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.model.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SataService {

    @POST("/auth")
    Call<UserResponse> login();

    @Multipart
    @POST("/users")
    Call<ResponseBody> register(@Part MultipartBody.Part avatar,
                                @Part("name") RequestBody name,
                                @Part("email") RequestBody email,
                                @Part("password") RequestBody password);

    @Multipart
    @POST("/users")
    Call<ResponseBody> registerWithOutUri(@Part("name") RequestBody name,
                                @Part("email") RequestBody email,
                                @Part("password") RequestBody password);
}

