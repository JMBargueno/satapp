package com.dmtroncoso.satapp.tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ticket {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("creado_por")
    @Expose
    private CreadoPor creadoPor;
    @SerializedName("fecha_creacion")
    @Expose
    private String fechaCreacion;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("titulo")
    @Expose
    private String titulo;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("inventariable")
    @Expose
    private String inventariable;
    @SerializedName("anotaciones")
    @Expose
    private List<Anotaciones> anotaciones = null;
    @SerializedName("asignaciones")
    @Expose
    private List<Object> asignaciones = null;
    @SerializedName("fotos")
    @Expose
    private List<String> fotos = null;
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

    public CreadoPor getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(CreadoPor creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getInventariable() {
        return inventariable;
    }

    public void setInventariable(String inventariable) {
        this.inventariable = inventariable;
    }

    public List<Anotaciones> getAnotaciones() {
        return anotaciones;
    }

    public void setAnotaciones(List<Anotaciones> anotaciones) {
        this.anotaciones = anotaciones;
    }

    public List<Object> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(List<Object> asignaciones) {
        this.asignaciones = asignaciones;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
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
