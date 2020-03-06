package com.dmtroncoso.satapp.inventariables;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.data.InventariableRepository;

import okhttp3.ResponseBody;

public class InventariableDetalleViewModel extends AndroidViewModel {

    MutableLiveData<ResponseBody> inventariable;
    InventariableRepository inventariableRepository;

    public InventariableDetalleViewModel(@NonNull Application application) {
        super(application);
        inventariableRepository = new InventariableRepository();
        inventariable = new MutableLiveData<>();
    }

    public MutableLiveData<ResponseBody> getInventariable(int idInventariable) {
        inventariable = inventariableRepository.getInventariable(idInventariable);
        return inventariable;
    }
}
