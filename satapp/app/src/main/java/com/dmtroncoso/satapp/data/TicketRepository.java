package com.dmtroncoso.satapp.data;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.service.SataService;
import com.dmtroncoso.satapp.tickets.Ticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketRepository {
    LiveData<List<Ticket>> allTickets;
    LiveData<List<Ticket>> ticketsUser;
    SataService sataService;


    public TicketRepository() {
        sataService = ServiceGenerator.createServiceTicket(SataService.class);
        allTickets = getAllTickets();
        ticketsUser = getTicketByUser();
    }

    public LiveData<List<Ticket>> getAllTickets() {
        final MutableLiveData<List<Ticket>> dataTicket = new MutableLiveData<>();

        Call<List<Ticket>> call = sataService.getAllTickets();
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if(response.isSuccessful()){
                    dataTicket.setValue(response.body());
                }else{
                    Toast.makeText(MyApp.getContext(), "No se ha podido obtener resultados del api", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        return dataTicket;
    }

    public LiveData<List<Ticket>> getTicketByUser() {
        final MutableLiveData<List<Ticket>> dataTicketUser = new MutableLiveData<>();

        Call<List<Ticket>> call = sataService.getTicketsByUser();
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if(response.isSuccessful()){
                    dataTicketUser.setValue(response.body());
                }else{
                    Toast.makeText(MyApp.getContext(), "No se ha podido obtener resultados del api", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

        return dataTicketUser;
    }
}
