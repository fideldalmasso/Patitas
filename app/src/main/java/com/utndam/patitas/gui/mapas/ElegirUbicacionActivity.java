package com.utndam.patitas.gui.mapas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.model.LatLng;
import com.utndam.patitas.R;

public class ElegirUbicacionActivity extends AppCompatActivity {

    private boolean allowBack=true;

//    @Override
//    public void onBackPressed() {
//        if(allowBack){
//            super.onBackPressed();
//        }
//        else{
//            this.finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MapsCompletoFragment frag = new MapsCompletoFragment();
        super.onCreate(savedInstanceState);

        boolean permitirMovimiento = true;
//        if(savedInstanceState==null){
            Bundle extras = getIntent().getExtras();
            if(extras!=null){
//                allowBack = extras.getBoolean("permitirMovimiento",true);
                double latitud = extras.getDouble("latitud",0.0);
                double longitud = extras.getDouble("longitud",0.0);

                if(latitud!= 0 && longitud != 0){
                    LatLng ubicacionFija = new LatLng(latitud,longitud);
                    frag = new MapsCompletoFragment(ubicacionFija);
                }
            }
//        }

        setContentView(R.layout.activity_elegir_ubicacion);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_mapa_completo,frag)
                .commit();
    }
}