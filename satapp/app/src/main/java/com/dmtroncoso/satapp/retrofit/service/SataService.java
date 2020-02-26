package com.dmtroncoso.satapp.retrofit.service;

import com.dmtroncoso.satapp.retrofit.model.Ticket;
import com.dmtroncoso.satapp.retrofit.model.TicketResponse;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.model.UserResponse;
import com.dmtroncoso.satapp.retrofit.model.register.UserRegister;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SataService {

    @POST("/auth")
    Call<UserResponse> login();

    @Multipart
    @POST("/ticket")
    Call<TicketResponse> nuevoTicket(@Body Ticket ticket);

    @POST("/users")
    Call<User> register(@Body UserRegister userRegister);
}
