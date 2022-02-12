package com.utndam.patitas.model;

public class MensajeModel {
    public  int remitenteFoto;
    public String remitenteFotoUrl;
    public  String remitenteNombre;
    public  String publicacionAsociada;
    public String idPublicacionAsociada;
    public  String contenido;
    public  String contacto;
    public String idReceptor;
    public String idRemitente;

    public MensajeModel(int remitenteFoto, String remitenteNombre, String contenido,  String publicacionAsociada, String contacto) {
        this.remitenteFoto = remitenteFoto;
        this.remitenteNombre = remitenteNombre;
        this.contenido = contenido;
        this.publicacionAsociada = publicacionAsociada;
        this.contacto = contacto;
    }

    public MensajeModel(){}

    public int getRemitenteFoto() {
        return remitenteFoto;
    }

    public void setRemitenteFoto(int remitenteFoto) {
        this.remitenteFoto = remitenteFoto;
    }

    public String getRemitenteFotoUrl() {
        return remitenteFotoUrl;
    }

    public void setRemitenteFotoUrl(String remitenteFotoUrl) {
        this.remitenteFotoUrl = remitenteFotoUrl;
    }

    public String getRemitenteNombre() {
        return remitenteNombre;
    }

    public void setRemitenteNombre(String remitenteNombre) {
        this.remitenteNombre = remitenteNombre;
    }

    public String getPublicacionAsociada() {
        return publicacionAsociada;
    }

    public void setPublicacionAsociada(String publicacionAsociada) {
        this.publicacionAsociada = publicacionAsociada;
    }

    public String getIdPublicacionAsociada() {
        return idPublicacionAsociada;
    }

    public void setIdPublicacionAsociada(String idPublicacionAsociada) {
        this.idPublicacionAsociada = idPublicacionAsociada;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getIdReceptor() {
        return idReceptor;
    }

    public void setIdReceptor(String idReceptor) {
        this.idReceptor = idReceptor;
    }

    public String getIdRemitente() {
        return idRemitente;
    }

    public void setIdRemitente(String idRemitente) {
        this.idRemitente = idRemitente;
    }

    @Override
    public String toString() {
        return contenido;
    }
}