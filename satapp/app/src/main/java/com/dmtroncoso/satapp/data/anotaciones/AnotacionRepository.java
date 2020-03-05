package com.dmtroncoso.satapp.data.anotaciones;



import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.dmtroncoso.satapp.tickets.Anotaciones;
import com.dmtroncoso.satapp.tickets.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnotacionRepository {
    LiveData<List<Anotaciones>> allAnotaciones;
    SataService sataService;
    String idTicket;

    public AnotacionRepository() {
        idTicket = SharedPreferencesManager.getSomeStringValue("idTicket");
        sataService = ServiceGenerator.createServiceTicket(SataService.class);
        allAnotaciones = getAllAnotaciones(idTicket);
    }

    public LiveData<List<Anotaciones>> getAllAnotaciones(String idTicket) {
        final MutableLiveData<List<Anotaciones>> dataAnotaciones = new MutableLiveData<>();

        Call<Ticket> call = sataService.getTicketById(idTicket);
        call.enqueue(new Callback<Ticket>() {
            @Override
            public void onResponse(Call<Ticket> call, Response<Ticket> response) {
                if(response.isSuccessful()){
                    dataAnotaciones.setValue(response.body().getAnotaciones());
                }
            }

            @Override
            public void onFailure(Call<Ticket> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        return dataAnotaciones;
    }
}
