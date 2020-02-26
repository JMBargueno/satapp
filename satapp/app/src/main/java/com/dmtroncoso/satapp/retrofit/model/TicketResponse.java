
package com.dmtroncoso.satapp.retrofit.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("creado_por")
    @Expose
    private User user;
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

    /**
     * No args constructor for use in serialization
     *
     */
    public TicketResponse() {
    }

    /**
     * 
     * @param user
     * @param descripcion
     * @param createdAt
     * @param estado
     * @param fechaCreacion
     * @param titulo
     * @param id
     * @param asignaciones
     * @param fotos
     * @param updatedAt
     */
    public TicketResponse(String id, User user, String fechaCreacion, String estado, String titulo, String descripcion, List<Object> asignaciones, List<String> fotos, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.user = user;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.asignaciones = asignaciones;
        this.fotos = fotos;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
