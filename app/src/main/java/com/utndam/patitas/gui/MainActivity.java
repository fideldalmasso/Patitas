package com.utndam.patitas.gui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.home.AltaPublicacionFragment;
import com.utndam.patitas.gui.home.BlankFragment;
import com.utndam.patitas.gui.home.HomePerdidosFragment;
import com.utndam.patitas.gui.ingreso.IngresoActivity;
import com.utndam.patitas.gui.ingreso.MapsFragment;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import android.icu.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    MaterialToolbar toolbar;
    DrawerLayout drawer;
    NavigationBarView bottomBar2;
    FragmentManager fragmentManager;
    HomePerdidosFragment homePerdidos;
    BlankFragment blankFrag;
    MapsFragment mapaFrag;
    AltaPublicacionFragment altaPublicacionFragment;
    private static int REQUEST_IMAGE_CAPTURE = 1;
    private static int REQUEST_IMAGE_SAVE = 2;
    String pathFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        setTheme(R.style.DarkThemePatitas);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkThemePatitas);
        } else {
            setTheme(R.style.LightThemePatitas);
        }
//
//        switch1 = findViewById(R.id.tema_oscuro);
//
//        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b){
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//                }
//            }
//        });





        setContentView(R.layout.activity_main);

        homePerdidos = new HomePerdidosFragment();
        Bundle b = new Bundle();
        b.putInt("column-count",2); //2 columnas
        homePerdidos.setArguments(b);
        blankFrag = new BlankFragment();
        mapaFrag = new MapsFragment();
        altaPublicacionFragment = new AltaPublicacionFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_fragmento,homePerdidos)
//                .addToBackStack(null)
                .commit();

        toolbar = findViewById(R.id.topAppBar);
        toolbar.setTitle("Home > Perdidos");
//        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer((int) Gravity.LEFT);

            }
        });


        NavigationView drawer2 = findViewById(R.id.left_drawer);
//        drawer2.setItemTextColor();

        bottomBar2 = findViewById(R.id.bottom_toolbar);

//        bottomBar = findViewById(R.id.bottom_toolbar);
        Menu bottomMenu = bottomBar2.getMenu();
        getMenuInflater().inflate(R.menu.menu_toolbar_inferior, bottomMenu);

    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_superior,menu);



//        for (int i = 0; i < bottomMenu.size(); i++) {
//            bottomMenu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    return onOptionsItemSelected(item);
//                }
//            });
//        }

        bottomBar2.setSelectedItemId(R.id.boton_home);
        bottomBar2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                int id = menuItem.getItemId();

                MenuItem luz = toolbar.getMenu().findItem(R.id.tema_oscuro);
                if(id == R.id.boton_home) luz.setVisible(true);
                else luz.setVisible(false);

                switch (id){
                    case R.id.boton_home:
                        toolbar.setTitle("Home > Perdidos");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,homePerdidos)
//                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.boton_buscar:
                        toolbar.setTitle("Buscar");
//                        Toast.makeText(getBaseContext(),"xd",Toast.LENGTH_LONG).show();
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,blankFrag)
//                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.boton_chats:
                        toolbar.setTitle("Chats");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,blankFrag)
//                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.boton_ajustes:
                        toolbar.setTitle("Ajustes");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,mapaFrag)
//                                .addToBackStack(null)
                                .commit();
                        break;
                }
                return true;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tema_oscuro:{

//                 item.setVisible(false);
//                if(toolbar.getTitle() != "Home > Perdidos"){
//                    System.out.println("HOLA");
//                }
//                else
                    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    setTheme(R.style.LightThemePatitas);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    setTheme(R.style.DarkThemePatitas);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
//                bottomBar2.setSelectedItemId(R.id.home);
//                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.map);
//                if(fragment != null)
//                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
////                Intent intent = new Intent(this, MainActivity.class);
////                startActivity(intent);
////                finish();
//                this.recreate();
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


    public void getImagen(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) { }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.utndam.patitas",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_SAVE);
            }
        }
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                dir /* directory */
        );
        pathFoto = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int reqCode,int resCode, Intent data) {
        super.onActivityResult(reqCode,resCode,data);
        if (reqCode == REQUEST_IMAGE_CAPTURE && resCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //this.altaPublicacionFragment.setImagen(imageBitmap);
        }
        if (reqCode == REQUEST_IMAGE_SAVE && resCode == RESULT_OK) {
            File file = new File(pathFoto);
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media
                        .getBitmap(getContentResolver(),
                                Uri.fromFile(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (imageBitmap != null) {
                this.altaPublicacionFragment.setImagen(imageBitmap);
            }
        }
    }

    public AltaPublicacionFragment getAltaPublicacionFragment() {
        return altaPublicacionFragment;
    }
}