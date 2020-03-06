package com.dmtroncoso.satapp.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestAsignarTecnico {

    @SerializedName("tecnico_id")
    @Expose
    private String tecnico_id;

    public RequestAsignarTecnico(String tecnico_id) {
        this.tecnico_id = tecnico_id;
    }

    public String getTecnico_id() {
        return tecnico_id;
    }

    public void setTecnico_id(String tecnico_id) {
        this.tecnico_id = tecnico_id;
    }
}
