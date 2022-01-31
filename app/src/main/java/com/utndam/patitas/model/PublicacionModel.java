package com.utndam.patitas.model;

public class PublicacionModel {
    public final int pImagen;
    public final String pTitulo;
    public final String pSecundario;
    public final String pSoporte;

    public PublicacionModel(int imagen, String titulo, String secundario, String soporte) {
        this.pImagen = imagen;
        this.pTitulo = titulo;
        this.pSecundario = secundario;
        this.pSoporte = soporte;
    }

    @Override
    public String toString() {
        return pSecundario;
    }
}
