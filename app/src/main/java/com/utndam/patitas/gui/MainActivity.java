package com.utndam.patitas.gui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.utndam.patitas.gui.home.BlankFragment;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.home.HomePerdidosFragment;
import com.utndam.patitas.gui.ingreso.MapsFragment;
import com.utndam.patitas.gui.ingreso.SignUpFragment;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    MaterialToolbar toolbar;
    DrawerLayout drawer;
    NavigationBarView bottomBar2;
    FragmentManager fragmentManager;
    HomePerdidosFragment homePerdidos;
    BlankFragment blankFrag;
    MapsFragment mapaFrag;
    Fragment loginFrag;



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
        loginFrag = new SignUpFragment();


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
                                .replace(R.id.contenedor_fragmento,loginFrag)
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
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}