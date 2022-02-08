package com.utndam.patitas.model;

public class PublicacionModel {
    public int pImagen;
    public String pTitulo;
    public String pSecundario;
    public String pSoporte;
    public String[] urlImagen;
    public String id;

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
