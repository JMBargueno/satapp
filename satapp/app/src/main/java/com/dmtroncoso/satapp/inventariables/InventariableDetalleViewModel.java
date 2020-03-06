package com.dmtroncoso.satapp.inventariables;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.data.InventariableRepository;
import com.dmtroncoso.satapp.retrofit.model.Inventariable;

import okhttp3.ResponseBody;

public class InventariableDetalleViewModel extends AndroidViewModel {

    MutableLiveData<Inventariable> inventariable;
    InventariableRepository inventariableRepository;

    public InventariableDetalleViewModel(@NonNull Application application) {
        super(application);
        inventariableRepository = new InventariableRepository();
        inventariable = new MutableLiveData<>();
    }

    public MutableLiveData<Inventariable> getInventariable(int idInventariable) {
        inventariable = inventariableRepository.getOneInventariable(idInventariable);
        return inventariable;
    }
}
