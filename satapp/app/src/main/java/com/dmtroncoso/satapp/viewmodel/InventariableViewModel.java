package com.dmtroncoso.satapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.data.InventariableRepository;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;

import java.util.List;

public class InventariableViewModel extends AndroidViewModel {

    private InventariableRepository inventariableRepository;
    private MutableLiveData<List<InventariableResponse>> listInv;


    public InventariableViewModel(@NonNull Application application) {
        super(application);
        inventariableRepository = new InventariableRepository();
        listInv = new MutableLiveData<>();
    }

    public MutableLiveData<List<InventariableResponse>> getInventariables(){
        listInv = inventariableRepository.getAllInventariables();
        return listInv;
    }
}
