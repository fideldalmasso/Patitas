package com.utndam.patitas.model;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.List;

public class PublicacionModel {
    public int pImagen;
    public String pTitulo;
    public String pSecundario;
    public String pSoporte;
    public List<String> urlImagen;
    public String id;
    public String descripcion;
    public String idUsuario;
    public String tipoAnimal;
    public String tipoPublicacion;
    @ServerTimestamp
    public Date fecha;
    public Long latitud;
    public Long longitud;

    public PublicacionModel(int imagen, String titulo, String secundario, String soporte) {
        this.pImagen = imagen;
        this.pTitulo = titulo;
        this.pSecundario = secundario;
        this.pSoporte = soporte;
    }



    public PublicacionModel() {   }

    @Override
    public String toString() {
        return pSecundario;
    }
}
