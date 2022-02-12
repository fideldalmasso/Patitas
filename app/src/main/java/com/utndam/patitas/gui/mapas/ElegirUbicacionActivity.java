package com.utndam.patitas.gui.mapas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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
        super.onCreate(savedInstanceState);

//        if(savedInstanceState==null){
//            Bundle extras = getIntent().getExtras();
//            if(extras!=null){
//                allowBack = extras.getBoolean("allowBack",true);
//            }
//        }

        setContentView(R.layout.activity_elegir_ubicacion);

        MapsCompletoFragment frag = new MapsCompletoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_mapa_completo,frag)
                .commit();
    }
}