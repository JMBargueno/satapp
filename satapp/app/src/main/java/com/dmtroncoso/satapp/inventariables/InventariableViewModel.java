package com.dmtroncoso.satapp.inventariables;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.dmtroncoso.satapp.data.InventariableRepository;
import com.dmtroncoso.satapp.retrofit.model.Inventariable;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;
import com.dmtroncoso.satapp.retrofit.model.RequestEditInventariable;

import java.util.ArrayList;
import java.util.List;

public class InventariableViewModel extends AndroidViewModel {

    MutableLiveData<List<InventariableResponse>> inventariableList;
    InventariableRepository inventariableRepository;
    MutableLiveData<String> idInventoriableSeleccionado;
    MutableLiveData<InventariableResponse> inventariable;
    ArrayList<String> tipos;

    public InventariableViewModel(@NonNull Application application) {
        super(application);
        inventariableRepository = new InventariableRepository();
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

    public void deleteInventariable(String id){
        inventariableList.setValue(inventariableRepository.deleteInventariable(id));
    }

    public MutableLiveData<InventariableResponse> getInventariableById(String id){
        inventariable = inventariableRepository.getInventariableById(id);
        return inventariable;
    }
    public void updateInventariable(String id, RequestEditInventariable req){
        inventariableRepository.updateInventariable(id, req);
    }

    public void openDialogInventariableMenu(Context ctx,String id){
        ModalBottomInventariableFragment dialogInventariable = ModalBottomInventariableFragment.newInstance(id);
        dialogInventariable.show(((AppCompatActivity)ctx).getSupportFragmentManager(),"ModalBottomInventariableFragment");
    }
    public ArrayList<String> getTipos(){
        tipos = inventariableRepository.getTipos();
        return tipos;
    }
}
