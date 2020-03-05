package com.dmtroncoso.satapp.data;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.retrofit.model.UserResponse;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    SataService service;
    MutableLiveData<List<User>> listUsersNoVal;
    MutableLiveData<ResponseBody> imagenes;


    public UserRepository() {
        service = ServiceGenerator.createService(SataService.class);
        listUsersNoVal = getUsersNoVal();

    }

    public MutableLiveData<List<User>> getUsersNoVal(){
        final MutableLiveData data = new MutableLiveData<>();
        Call<List<User>> call = service.getNonValidate();

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    data.setValue(response.body());
                    Log.d("LISTA",response.body().get(0).getName());

                }else{
                    Toast.makeText(MyApp.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });
        return data;
    }

    public MutableLiveData<ResponseBody> getImagenes(String idUser){
        final MutableLiveData data = new MutableLiveData<>();
        Call<ResponseBody> call = service.getUserAvatar(idUser);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "No se pudo cargar la imagen", Toast.LENGTH_SHORT).show();

            }
        });

        return data;

    }


}

