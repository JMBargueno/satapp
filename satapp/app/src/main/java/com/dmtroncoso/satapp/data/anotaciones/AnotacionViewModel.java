package com.dmtroncoso.satapp.data.anotaciones;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.tickets.Anotaciones;

import java.util.List;


public class AnotacionViewModel extends AndroidViewModel {
    AnotacionRepository anotacionRepository;
    LiveData<List<Anotaciones>> anotaciones;
    String idTicket;

    public AnotacionViewModel(@NonNull Application application) {
        super(application);

        idTicket = SharedPreferencesManager.getSomeStringValue("idTicket");
        anotacionRepository = new AnotacionRepository();
        anotaciones = anotacionRepository.getAllAnotaciones(idTicket);
    }

    public LiveData<List<Anotaciones>> getAnotaciones(){
        return anotaciones;
    }
}
