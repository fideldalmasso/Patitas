package com.utndam.patitas.gui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.home.HomeFragment;
import com.utndam.patitas.gui.ingreso.IngresoActivity;
import com.utndam.patitas.gui.mensajes.MisMensajesFragment;
import com.utndam.patitas.model.UsuarioModel;
import com.utndam.patitas.service.CloudFirestoreService;


public class MainActivity extends AppCompatActivity {


    private MaterialToolbar barraSuperior;
    private DrawerLayout drawer;
    private NavigationBarView barraInferior;
    private FragmentManager fragmentManager;
    private HomeFragment homeFrag;
    private BlankFragment blankFrag;
    private MisMensajesFragment mensajesFrag;
    private int pantallaActual;
    public UsuarioModel usuarioModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(savedInstanceState!=null)
            savedInstanceState.clear();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //asignar tema correspondiente
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemePatitas);
        } else {
            setTheme(R.style.LightThemePatitas);
        }


        pantallaActual=R.id.boton_home;

        //cargar fragmentos
        homeFrag = HomeFragment.newInstance(2);
        blankFrag = new BlankFragment();
        mensajesFrag = new MisMensajesFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_fragmento, homeFrag)
                .commit();

        //barra superior
        barraSuperior = findViewById(R.id.topAppBar);
        barraSuperior.setTitle("Home > Perdidos");
        setSupportActionBar(barraSuperior);

        //drawer
        drawer = findViewById(R.id.drawer_layout);
        barraSuperior.setNavigationOnClickListener(v -> drawer.openDrawer(Gravity.LEFT));

        //barra inferior
        barraInferior = findViewById(R.id.bottom_toolbar);
        Menu barraInferiorMenu = barraInferior.getMenu();
        getMenuInflater().inflate(R.menu.menu_toolbar_inferior, barraInferiorMenu);
        barraInferior.setSelectedItemId(R.id.boton_home);
        barraInferior.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                int id = menuItem.getItemId();
                if(id == pantallaActual) //no hacer nada si se selecciona la pantalla actual
                    return true;

                pantallaActual=id;

                switch (id){
                    case R.id.boton_home:
                        barraSuperior.setTitle("Home > Perdidos");

                        //quitar todos los fragmentos basura
                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }

                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento, homeFrag)
                                .commit();
                        break;
                    case R.id.boton_buscar:
                        barraSuperior.setTitle("Buscar");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,blankFrag)
                                .commit();
                        break;
                    case R.id.boton_chats:
                        barraSuperior.setTitle("Chats");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,mensajesFrag)
                                .commit();
                        break;
                    case R.id.boton_ajustes:
                        barraSuperior.setTitle("Ajustes");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,blankFrag)
                                .commit();
                        break;
                }
                return true;
            }
        });
        CloudFirestoreService cloudFirestoreService = new CloudFirestoreService();
        FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
        cloudFirestoreService.buscarUsuario(usuario.getEmail(),usuario.getProviderId(),this);

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

                pedirConfirmacionCambioTema();

                return true;
            }
            case R.id.logout:{
                //cerrar sesion
                FirebaseAuth.getInstance().signOut();

                //reiniciar aplicacion
                Intent i = new Intent(this, IngresoActivity.class);
                startActivity(i);
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void pedirConfirmacionCambioTema(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setCancelable(false)
                .setTitle("¿Cambiar de tema?")
                .setMessage("La app se reiniciará")
                .setPositiveButton("Continuar", (dialog, i) -> {
                    dialog.dismiss();
                    cambiarTema();
                })
                .setNegativeButton("Cancelar", (dialog, id) -> {
                    dialog.dismiss();
                })
                .create()
                .show();

    }


    private void cambiarTema(){
        //cambiar el tema
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        //reiniciar la actividad
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void setUsuarioModel(UsuarioModel usuarioModel) {
        this.usuarioModel = usuarioModel;
    }
}