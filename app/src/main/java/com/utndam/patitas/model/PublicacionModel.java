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
    public String urlImagen;
    public String id;
    public String descripcion;
    public String idUsuario;
    public String tipoAnimal;
    public String tipoPublicacion;
    @ServerTimestamp
    public Date fecha;
    public Double latitud;
    public Double longitud;

    public PublicacionModel(int imagen, String titulo, String secundario, String soporte) {
        this.pImagen = imagen;
        this.pTitulo = titulo;
        this.pSecundario = secundario;
        this.pSoporte = soporte;
    }

    public int getpImagen() {
        return pImagen;
    }

    public void setpImagen(int pImagen) {
        this.pImagen = pImagen;
    }

    public String getpTitulo() {
        return pTitulo;
    }

    public void setpTitulo(String pTitulo) {
        this.pTitulo = pTitulo;
    }

    public String getpSecundario() {
        return pSecundario;
    }

    public void setpSecundario(String pSecundario) {
        this.pSecundario = pSecundario;
    }

    public String getpSoporte() {
        return pSoporte;
    }

    public void setpSoporte(String pSoporte) {
        this.pSoporte = pSoporte;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

    public String getTipoPublicacion() {
        return tipoPublicacion;
    }

    public void setTipoPublicacion(String tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public PublicacionModel() {   }

    @Override
    public String toString() {
        return pSecundario;
    }

}
