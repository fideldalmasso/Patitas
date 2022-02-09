package com.utndam.patitas.model;

public class UsuarioModel {
    public String nombreCompleto;
    public String fotoUrl;
    public String telefono;
    public String id;
    public String mail;
    public String tipoCuenta;

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
