package com.dmtroncoso.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.dmtroncoso.satapp.data.InventariableRepository;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;

import java.util.List;

public class InventariableViewModel extends AndroidViewModel {

    MutableLiveData<PagedList<InventariableResponse>> inventariableList;
    InventariableRepository inventariableRepository;
    MutableLiveData<String> idInventoriableSeleccionado;

    public InventariableViewModel(@NonNull Application application) {
        super(application);
        inventariableRepository = new InventariableRepository();
        this.idInventoriableSeleccionado = new MutableLiveData<>();
        this.idInventoriableSeleccionado.setValue(null);
    }

    public MutableLiveData<PagedList<InventariableResponse>> getInventariableList() {
        inventariableList = inventariableRepository.getAllInventariables(5);
        return inventariableList;
    }

    public void setIdInventoriableSeleccionado(String idInventoriableSeleccionado) {
        this.idInventoriableSeleccionado.setValue(idInventoriableSeleccionado);
    }

    public MutableLiveData<String> getIdInventoriableSeleccionado() {
        return idInventoriableSeleccionado;
    }
}
