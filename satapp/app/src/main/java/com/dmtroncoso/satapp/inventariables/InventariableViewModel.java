package com.dmtroncoso.satapp.inventariables;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.dmtroncoso.satapp.data.InventariableRepository;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;

import java.util.List;

public class InventariableViewModel extends AndroidViewModel {

    MutableLiveData<List<InventariableResponse>> inventariableList;
    InventariableRepository inventariableRepository;
    MutableLiveData<String> idInventoriableSeleccionado;

    public InventariableViewModel(@NonNull Application application) {
        super(application);
        inventariableRepository = new InventariableRepository();
        this.idInventoriableSeleccionado = new MutableLiveData<>();
        this.idInventoriableSeleccionado.setValue(null);
    }

    public MutableLiveData<List<InventariableResponse>> getInventariableList(int page, int limit) {
        inventariableList = inventariableRepository.getAllInventariablesPaginable(page,limit);
        return inventariableList;
    }

    public void setIdInventoriableSeleccionado(String idInventoriableSeleccionado) {
        this.idInventoriableSeleccionado.setValue(idInventoriableSeleccionado);
    }

    public MutableLiveData<String> getIdInventoriableSeleccionado() {
        return idInventoriableSeleccionado;
    }

    public void deleteInventariable(String id){
        inventariableRepository.deleteInventariable(id);
    }

    public void openDialogInventariableMenu(Context ctx,String id){
        ModalBottomInventariableFragment dialogInventariable = ModalBottomInventariableFragment.newInstance(id);
        dialogInventariable.show(((AppCompatActivity)ctx).getSupportFragmentManager(),"ModalBottomInventariableFragment");
    }
}
