package com.utndam.patitas.gui.mapas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.utndam.patitas.R;

public class ElegirUbicacionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_ubicacion);

        MapsCompletoFragment frag = new MapsCompletoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_mapa_completo,frag)
                .commit();
    }
}