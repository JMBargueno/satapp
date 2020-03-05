package com.dmtroncoso.satapp.retrofit.service;


import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;

import com.dmtroncoso.satapp.retrofit.model.TicketResponse;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.model.UserResponse;
import com.dmtroncoso.satapp.tickets.Ticket;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface SataService {

    @POST("/auth")
    Call<UserResponse> login();

    @Multipart
    @POST("/users")
    Call<User> register(@Part MultipartBody.Part avatar,
                                @Part("name") RequestBody name,
                                @Part("email") RequestBody email,
                                @Part("password") RequestBody password);




    @GET("/users")
    Call<List<User>> getAllUsers();

    @GET("/users/no-validated")
    Call<List<User>> getNoValUsers();

    @GET("/users/img/{idUsuarioSeleccionado}")
    Call<ResponseBody> getUserAvatar(@Path ("idUsuarioSeleccionado") String id);


    @GET("/inventariable")
    Call<List<InventariableResponse>> getInventariables();



    @Multipart
    @POST("/inventariable")
    Call<ResponseBody> uploadInventariable(@Part MultipartBody.Part imagen,
                                           @Part("ubicacion") RequestBody ubicacion,
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

    @GET("/ticket/{id}")
    Call<Ticket> getTicketById(@Path("id") String id);

    @GET("/ticket/img/{id}/{index}")
    Call<ResponseBody> getImageOfTicket(@Path("id") String idImage, @Path("index") int index);

    @GET("/users/img/{id}")
    Call<ResponseBody> getImageOfUser(@Path("id") String idImage);

    @GET("/inventariable/{id}")
    Call<ResponseBody> getInventariableById(@Path("id") String idInv);

    @GET("/inventariable")
    Call<List<InventariableResponse>> getInventariablesPagedList(@Query("page") int page, @Query("limit") int limit);

    @DELETE("/inventariable/{id}")
    Call<Void> deleteInventariable(@Path("id") String id);


}

