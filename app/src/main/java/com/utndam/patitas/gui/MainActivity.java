package com.utndam.patitas.gui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.utndam.patitas.MyReceiver;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.busqueda.BusquedaFragment;
import com.utndam.patitas.gui.home.HomeFragment;
import com.utndam.patitas.gui.ingreso.IngresoActivity;
import com.utndam.patitas.gui.mensajes.MisMensajesFragment;
import com.utndam.patitas.model.UsuarioActual;
import com.utndam.patitas.service.CloudFirestoreService;
import com.utndam.patitas.service.CloudStorageService;


public class MainActivity extends AppCompatActivity implements UsuarioActual.UsuarioActualCargadoListener {


    private MaterialToolbar barraSuperior;
    private DrawerLayout drawerLayout;
    private NavigationBarView barraInferior;
    private FragmentManager fragmentManager;
    private HomeFragment homeFrag;
    private BusquedaFragment busquedaFrag;
    private BlankFragment blankFrag;
    private SettingsFragment settingsFragment;
    private MisMensajesFragment mensajesFrag;
    private TextView drawerEmailUsuario;
    private TextView drawerNombreCompletoUsuario;
    private ImageView drawerFotoUsuario;

    private int pantallaActual;
//    public UsuarioModel usuarioModel;
    public static String CHANNEL_ID= "Canal_Patitas";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(savedInstanceState!=null)
            savedInstanceState.clear();
        super.onCreate(savedInstanceState);

        UsuarioActual.getInstance().setListener(this);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        BroadcastReceiver br = new MyReceiver();
        IntentFilter filtro = new IntentFilter();
        filtro.addAction(MyReceiver.EVENTO_PUBLICACION_CREADA);
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(br,filtro);



        //asignar tema correspondiente

        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                setTheme(R.style.DarkThemePatitas);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                setTheme(R.style.LightThemePatitas);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }

//        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
//            setTheme(R.style.DarkThemePatitas);
//        } else {
//            setTheme(R.style.LightThemePatitas);
//        }


        pantallaActual=R.id.boton_home;

        //cargar fragmentos
        homeFrag = HomeFragment.newInstance(2);
//        busquedaFrag = new BusquedaFragment();
        blankFrag = new BlankFragment();
        settingsFragment = new SettingsFragment();
        mensajesFrag = new MisMensajesFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_fragmento, homeFrag)
                .commit();

        //barra superior
        barraSuperior = findViewById(R.id.topAppBar);
        barraSuperior.setTitle("Home");
        setSupportActionBar(barraSuperior);

        //drawer

        drawerLayout = findViewById(R.id.drawer_layout);
        barraSuperior.setNavigationOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
        NavigationView drawer = findViewById(R.id.left_drawer);
        View drawerHeader = drawer.getHeaderView(0);
        drawerNombreCompletoUsuario = drawerHeader.findViewById(R.id.nombre_completo_usuario);
        drawerEmailUsuario = drawerHeader.findViewById(R.id.email_usuario);
        drawerFotoUsuario = drawerHeader.findViewById(R.id.foto_usuario);

//        drawer.setBackgroundColor(R.attr.background);
//        drawerHeader.setBackgroundColor(R.attr.background);

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
                        barraSuperior.setTitle("Home");

                        //quitar todos los fragmentos basura
                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                        }

                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento, homeFrag)
                                .commit();
                        break;
                    case R.id.boton_buscar:
                        busquedaFrag = new BusquedaFragment();
                        barraSuperior.setTitle("Buscar");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento, busquedaFrag)
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
                                .replace(R.id.contenedor_fragmento,settingsFragment)
                                .commit();
                        break;
                }
                return true;
            }
        });

        cargarDatosUsuario();
    }


    private CloudFirestoreService service1 = new CloudFirestoreService();
    private CloudStorageService service2 = new CloudStorageService();

    public void cargarDatosUsuario(){

        service1.buscarUsuario(FirebaseAuth.getInstance().getCurrentUser().getEmail(),MainActivity.this);

//        new Thread(() -> {
//           FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
//           CloudFirestoreService cloudFirestoreService = new CloudFirestoreService();
////           MainActivity.this.runOnUiThread(() -> {
////               drawerNombreCompletoUsuario.setText(usuario.getDisplayName());
////               drawerEmailUsuario.setText(usuario.getEmail());
////               //falta cargar imagen de usuario con URL y setearla en drawerFotoUsuario
////
////           });
//       }).start();


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
            setTheme(R.style.LightThemePatitas);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            setTheme(R.style.DarkThemePatitas);
        }

        //reiniciar la actividad
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

//    public void setUsuarioModel(UsuarioModel usuarioModel) {
//        this.usuarioModel = usuarioModel;
//    }

    public void cambiarTextoBarraSuperior(String texto){
        if(!TextUtils.isEmpty(texto) && texto.length()>0)
            barraSuperior.setTitle(texto);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificaciones_patitas";
            String description = "";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    };

    @Override
    public void onUsuarioCargado() {
        UsuarioActual usuario = UsuarioActual.getInstance();
        drawerNombreCompletoUsuario.setText(usuario.getNombreCompleto());
        drawerEmailUsuario.setText(usuario.getMail());
        service2.setImagen(drawerFotoUsuario,usuario.getFotoUrl(),this);
    }



    public TextView getDrawerNombreCompletoUsuario() {
        return drawerNombreCompletoUsuario;
    }
}