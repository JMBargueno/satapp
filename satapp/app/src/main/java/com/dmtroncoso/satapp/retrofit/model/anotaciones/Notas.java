package com.dmtroncoso.satapp.retrofit.model.anotaciones;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notas {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("id_usuario")
    @Expose
    private IdUsuario idUsuario;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("cuerpo")
    @Expose
    private String cuerpo;
    @SerializedName("ticket")
    @Expose
    private Ticket ticket;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IdUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(IdUsuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
