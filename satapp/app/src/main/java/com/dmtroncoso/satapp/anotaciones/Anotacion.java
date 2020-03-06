package com.dmtroncoso.satapp.anotaciones;

public class Anotacion {

    private String id_ticket;
    private String cuerpo;

    public Anotacion(String id_ticket, String cuerpo) {
        this.id_ticket = id_ticket;
        this.cuerpo = cuerpo;
    }

    public String getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(String id_ticket) {
        this.id_ticket = id_ticket;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
}
