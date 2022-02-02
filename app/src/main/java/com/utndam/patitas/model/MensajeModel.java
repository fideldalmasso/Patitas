package com.utndam.patitas.model;

public class MensajeModel {
    public final int senderFoto;
    public final String senderNombre;
    public final String publicacionAsociada;
    public final String contenido;
    public final String contacto;

    public MensajeModel(int senderFoto, String senderNombre, String contenido,  String publicacionAsociada, String contacto) {
        this.senderFoto = senderFoto;
        this.senderNombre = senderNombre;
        this.contenido = contenido;
        this.publicacionAsociada = publicacionAsociada;
        this.contacto = contacto;
    }

    @Override
    public String toString() {
        return contenido;
    }
}
