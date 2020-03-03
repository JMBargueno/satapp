package com.dmtroncoso.satapp.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventariableRepository {
    SataService service;
    MutableLiveData<List<InventariableResponse>> listaInv;


    public InventariableRepository() {
        service = ServiceGenerator.createServiceTicket(SataService.class);
        listaInv = getAllInventariables();
    }

    public MutableLiveData<List<InventariableResponse>> getAllInventariables(){
        final MutableLiveData data = new MutableLiveData<>();
        Call<InventariableResponse> call = service.getInventariables();

        call.enqueue(new Callback<InventariableResponse>() {
            @Override
            public void onResponse(Call<InventariableResponse> call, Response<InventariableResponse> response) {

                if(response.isSuccessful()){
                    data.setValue(response.body());

                }else{
                    Toast.makeText(MyApp.getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<InventariableResponse> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error", Toast.LENGTH_SHORT).show();

            }
        });

        return data;
    }
}
