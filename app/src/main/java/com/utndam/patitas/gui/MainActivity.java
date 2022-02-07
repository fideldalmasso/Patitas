package com.utndam.patitas.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.home.AltaPublicacionFragment;
import com.utndam.patitas.gui.home.BlankFragment;
import com.utndam.patitas.gui.home.HomeFragment;
import com.utndam.patitas.gui.ingreso.IngresoActivity;
import com.utndam.patitas.gui.ingreso.MapsFragment;
import com.utndam.patitas.gui.mensajes.MensajesFragment;


public class MainActivity extends AppCompatActivity {


    MaterialToolbar toolbar;
    DrawerLayout drawer;
    NavigationBarView bottomBar2;
    FragmentManager fragmentManager;
    HomeFragment homePerdidos;
    BlankFragment blankFrag;
    MapsFragment mapaFrag;
    MensajesFragment mensajesFrag;
    AltaPublicacionFragment altaPublicacionFragment;
    int pantallaActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemePatitas);
        } else {
            setTheme(R.style.LightThemePatitas);
        }

        pantallaActual=R.id.boton_home;

        //cargar fragmentos
        homePerdidos = HomeFragment.newInstance(2);
        blankFrag = new BlankFragment();
        mapaFrag = new MapsFragment();
        altaPublicacionFragment = new AltaPublicacionFragment();
        mensajesFrag = new MensajesFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_fragmento,homePerdidos)
                .commit();

        //barra superior
        toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle("Home > Perdidos");
        setSupportActionBar(toolbar);

        //drawer
        drawer = findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(v -> drawer.openDrawer(Gravity.LEFT));

        //barra inferior
        bottomBar2 = findViewById(R.id.bottom_toolbar);
        Menu bottomMenu = bottomBar2.getMenu();
        getMenuInflater().inflate(R.menu.menu_toolbar_inferior, bottomMenu);
        bottomBar2.setSelectedItemId(R.id.boton_home);
        bottomBar2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                int id = menuItem.getItemId();
                if(id == pantallaActual) //no hacer nada si se selecciona la pantalla actual
                    return true;

                pantallaActual=id;

                switch (id){
                    case R.id.boton_home:
                        toolbar.setTitle("Home > Perdidos");

                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }

                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,homePerdidos)
                                .commit();
                        break;
                    case R.id.boton_buscar:
                        toolbar.setTitle("Buscar");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,blankFrag)
                                .commit();
                        break;
                    case R.id.boton_chats:
                        toolbar.setTitle("Chats");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,mensajesFrag)
                                .commit();
                        break;
                    case R.id.boton_ajustes:
                        toolbar.setTitle("Ajustes");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,mapaFrag)
                                .commit();
                        break;
                }
                return true;
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_superior,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tema_oscuro:{

                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }

                //reiniciar la actividad
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

                return true;
            }
            case R.id.logout:{
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, IngresoActivity.class);
                finish();
                startActivity(i);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}