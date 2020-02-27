package com.dmtroncoso.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.data.InventariableRepository;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;

import java.util.List;

public class InventariableViewModel extends AndroidViewModel {

    MutableLiveData<List<InventariableResponse>> inventariableList;
    InventariableRepository inventariableRepository;
    MutableLiveData<String> idInventoriableSeleccionado;

    public InventariableViewModel(@NonNull Application application, InventariableRepository inventariableRepository, MutableLiveData<List<InventariableResponse>> inventariableList, MutableLiveData<MutableLiveData<String>> idInventoriableSeleccionado) {
        super(application);
        this.inventariableRepository = inventariableRepository;
        this.inventariableList = inventariableList;
        this.idInventoriableSeleccionado = new MutableLiveData<>();
        this.idInventoriableSeleccionado.setValue(null);
    }

    public MutableLiveData<List<InventariableResponse>> getInventariableList() {
        inventariableList = inventariableRepository.getAllInventariables();
        return inventariableList;
    }

    public void setIdInventoriableSeleccionado(String idInventoriableSeleccionado) {
        this.idInventoriableSeleccionado.setValue(idInventoriableSeleccionado);
    }

    public MutableLiveData<String> getIdInventoriableSeleccionado() {
        return idInventoriableSeleccionado;
    }
}
