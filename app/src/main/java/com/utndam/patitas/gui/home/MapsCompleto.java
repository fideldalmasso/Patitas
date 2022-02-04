package com.utndam.patitas.gui.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.utndam.patitas.R;
import com.utndam.patitas.gui.ingreso.MapsFragment;

public class MapsCompleto extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_completo);

        MapsFragment frag = new MapsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_mapa_completo,frag)
                .commit();
    }
}