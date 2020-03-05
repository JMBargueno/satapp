package com.dmtroncoso.satapp.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dmtroncoso.satapp.tickets.Ticket;

import java.util.List;

public class TicketViewModel extends AndroidViewModel {
    TicketRepository ticketRepository;
    LiveData<List<Ticket>> tickets;
    LiveData<List<Ticket>> ticketsUser;

    public TicketViewModel(@NonNull Application application) {
        super(application);

        ticketRepository = new TicketRepository();
        tickets = ticketRepository.getAllTickets();
        ticketsUser = ticketRepository.getTicketByUser();
    }

    public LiveData<List<Ticket>> getTickets(){
        return tickets;
    }
    public LiveData<List<Ticket>> getTicketsUser(){
        return ticketsUser;
    }
}
