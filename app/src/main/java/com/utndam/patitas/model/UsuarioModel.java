package com.utndam.patitas.model;

public class UsuarioModel {
    private String nombreCompleto;
    private String fotoUrl;
    private String telefono;
    private String id;
    private String mail;

    // copy constructor
    public void copiar(UsuarioModel otro) {
        this.nombreCompleto = otro.nombreCompleto;
        this.fotoUrl = otro.fotoUrl;
        this.telefono = otro.telefono;
        this.id = otro.id;
        this.mail = otro.mail;
    }



    public String getInfoContacto(){
        StringBuilder res = new StringBuilder();
        res.append(nombreCompleto + "\n");
        if(telefono!=null)
            res.append("Teléfono: " + telefono + "\n");
        return res.toString();
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

    @Override
    public String toString() {
        return "UsuarioModel{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", fotoUrl='" + fotoUrl + '\'' +
                ", telefono='" + telefono + '\'' +
                ", id='" + id + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}
