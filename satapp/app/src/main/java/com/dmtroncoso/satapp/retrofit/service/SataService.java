package com.dmtroncoso.satapp.retrofit.service;

import com.dmtroncoso.satapp.retrofit.model.Inventariable;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;
import com.dmtroncoso.satapp.retrofit.model.TicketResponse;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.model.UserResponse;
import com.dmtroncoso.satapp.tickets.Ticket;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SataService {

    @POST("/auth")
    Call<UserResponse> login();

    @Multipart
    @POST("/users")
    Call<User> register(@Part MultipartBody.Part avatar,
                                @Part("name") RequestBody name,
                                @Part("email") RequestBody email,
                                @Part("password") RequestBody password);


    @GET("/inventariable")
    Call<InventariableResponse> getInventariables();

    @Multipart
    @POST("/inventariable")
    Call<ResponseBody> uploadInventariable(@Part MultipartBody.Part imagen,
                                           @Part("codigo") RequestBody codigo,
                                            @Part("tipo") RequestBody tipo,
                                            @Part("nombre") RequestBody nombre,
                                            @Part("descripcion") RequestBody descripcion);

    @Multipart
    @POST("/ticket")
    Call<TicketResponse> nuevoTicket(@Part List<MultipartBody.Part> fotos,
                                     @Part("titulo") RequestBody titulo,
                                     @Part("descripcion") RequestBody descripcion);

    @Multipart
    @POST("/ticket")
    Call<TicketResponse> nuevoTicketQR(@Part List<MultipartBody.Part> fotos,
                                       @Part("titulo") RequestBody titulo,
                                       @Part("descripcion") RequestBody descripcion,
                                       @Part("inventariable") RequestBody inventariable);

    @GET("/ticket")
    Call<List<Ticket>> getAllTickets();
}

