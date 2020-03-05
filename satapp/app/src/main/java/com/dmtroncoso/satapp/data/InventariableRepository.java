package com.dmtroncoso.satapp.data;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;
import com.dmtroncoso.satapp.retrofit.service.SataService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventariableRepository {
    SataService service;
    MutableLiveData<List<InventariableResponse>> listaInv;

    public InventariableRepository() {
        service = ServiceGenerator.createServiceTicket(SataService.class);
        listaInv = new MutableLiveData<>();
    }

    public MutableLiveData<List<InventariableResponse>> getAllInventariables(){
        Call<List<InventariableResponse>> call = service.getInventariables();
        call.enqueue(new Callback<List<InventariableResponse>>() {
            @Override
            public void onResponse(Call<List<InventariableResponse>> call, Response<List<InventariableResponse>> response) {
                if(response.isSuccessful()){
                    listaInv.setValue(response.body());
                }else{
                    Toast.makeText(MyApp.getContext(), "Se produjo un error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<InventariableResponse>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
        return listaInv;
    }

    public List<InventariableResponse> deleteInventariable(final String id){
        List<InventariableResponse> listInventariable = new ArrayList<>();
        Call<Void> call = service.deleteInventariable(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    List<InventariableResponse> lista = getAllInventariables().getValue();
                    listInventariable.addAll(lista);
                    Toast.makeText(MyApp.getContext(), "Equipo eliminado correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MyApp.getContext(), "Se produjo un error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        return listInventariable;
    }
}
