package com.utndam.patitas.model;

import com.google.android.gms.maps.model.LatLng;

public class UsuarioActual extends  UsuarioModel{

    private static UsuarioActual instancia = null;

    private LatLng ubicacionActual;
    private UsuarioActualCargadoListener listener;

    private UsuarioActual(){
        ubicacionActual = new LatLng(1,1);
    }

    public static UsuarioActual getInstance(){
        if(instancia==null){
            instancia=new UsuarioActual();
        }
        return instancia;
    }

    @Override
    public void copiar(UsuarioModel otro) {
        super.copiar(otro);
        listener.onUsuarioCargado();
    }

    public void setListener(UsuarioActualCargadoListener listener){
        this.listener = listener;
    }

    public LatLng getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(LatLng ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }

    public interface UsuarioActualCargadoListener {
        public void onUsuarioCargado( );
    }

}
