package com.dmtroncoso.satapp.tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Anotaciones {
    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
