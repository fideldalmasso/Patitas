package com.utndam.patitas.model;

public class MensajeModel {
    public final int remitenteFoto;
    public final String remitenteNombre;
    public final String publicacionAsociada;
    public final String contenido;
    public final String contacto;

    public MensajeModel(int remitenteFoto, String remitenteNombre, String contenido,  String publicacionAsociada, String contacto) {
        this.remitenteFoto = remitenteFoto;
        this.remitenteNombre = remitenteNombre;
        this.contenido = contenido;
        this.publicacionAsociada = publicacionAsociada;
        this.contacto = contacto;
    }

    @Override
    public String toString() {
        return contenido;
    }
}
