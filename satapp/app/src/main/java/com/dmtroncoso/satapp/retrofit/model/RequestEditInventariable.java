
package com.dmtroncoso.satapp.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestEditInventariable {

    @SerializedName("tipo")
    @Expose
    private String tipo;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("ubicacion")
    @Expose
    private String ubicacion;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RequestEditInventariable() {
    }

    /**
     * 
     * @param descripcion
     * @param tipo
     * @param ubicacion
     * @param nombre
     */
    public RequestEditInventariable(String tipo, String nombre, String descripcion, String ubicacion) {
        super();
        this.tipo = tipo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

}
