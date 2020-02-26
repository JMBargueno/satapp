package com.dmtroncoso.satapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.dmtroncoso.satapp.retrofit.generator.ServiceGenerator;
import com.dmtroncoso.satapp.retrofit.model.TicketResponse;
import com.dmtroncoso.satapp.retrofit.service.SataService;

public class SatApiRepository {
    private ServiceGenerator serviceGenerator;
    private SataService sataService;

    public SatApiRepository(){
        sataService =serviceGenerator.createService(SataService.class);

    }

}
