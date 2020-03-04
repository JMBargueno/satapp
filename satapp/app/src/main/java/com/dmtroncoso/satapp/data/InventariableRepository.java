package com.dmtroncoso.satapp.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

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
    MutableLiveData<PagedList<InventariableResponse>> listaInv;


    public InventariableRepository() {
        service = ServiceGenerator.createServiceTicket(SataService.class);
        listaInv = getAllInventariables(5);
    }

    public MutableLiveData<PagedList<InventariableResponse>> getAllInventariables(int page){
        final MutableLiveData data = new MutableLiveData<>();
        Call<PagedList<InventariableResponse>> call = service.getInventariablesPagedList(page);

        call.enqueue(new Callback<PagedList<InventariableResponse>>() {
            @Override
            public void onResponse(Call<PagedList<InventariableResponse>> call, Response<PagedList<InventariableResponse>> response) {

                if(response.isSuccessful()){
                    data.setValue(response.body());

                }else{
                    Toast.makeText(MyApp.getContext(), "Error en onResponse", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PagedList<InventariableResponse>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error onFailure", Toast.LENGTH_SHORT).show();

            }
        });

        return data;
    }
}
