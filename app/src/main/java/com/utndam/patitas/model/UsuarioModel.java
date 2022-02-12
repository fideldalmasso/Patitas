package com.utndam.patitas.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.Exclude;

public class UsuarioModel {
    private String nombreCompleto;
    private String fotoUrl;
    private String telefono;
    private String id;
    private String mail;
    private String tipoCuenta;
    @Exclude
    private LatLng ubicacionActual;

    public LatLng getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(LatLng ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public String getInfoContacto(){
        String resultado=nombreCompleto+"\n";
        if(telefono!=null)
            resultado+="Teléfono: "+telefono+"\n";
        return resultado;
    }

    public UsuarioModel(){}

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    @Override
    public String toString() {
        return "UsuarioModel{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", fotoUrl='" + fotoUrl + '\'' +
                ", telefono='" + telefono + '\'' +
                ", id='" + id + '\'' +
                ", mail='" + mail + '\'' +
                ", tipoCuenta='" + tipoCuenta + '\'' +
                '}';
    }
}
