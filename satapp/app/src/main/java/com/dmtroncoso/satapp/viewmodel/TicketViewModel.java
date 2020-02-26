package com.dmtroncoso.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.repository.SatApiRepository;
import com.dmtroncoso.satapp.retrofit.model.TicketResponse;

public class TicketViewModel extends AndroidViewModel {
    MutableLiveData<TicketResponse>ticket;
    SatApiRepository satApiRepository;

    public TicketViewModel(@NonNull Application application) {
        super(application);
    }
}
