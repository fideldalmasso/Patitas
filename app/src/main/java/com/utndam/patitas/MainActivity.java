package com.utndam.patitas;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    DrawerLayout drawer;
    BottomNavigationView bottomBar;
    NavigationBarView bottomBar2;
    FragmentManager fragmentManager;
    HomePerdidosFragment homePerdidos;
    BlankFragment blankFrag;
    MapsFragment2 mapaFrag;
    Fragment loginFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homePerdidos = new HomePerdidosFragment();
        blankFrag = new BlankFragment();
        mapaFrag = new MapsFragment2();
        loginFrag = new SignInFragment();


        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.contenedor_fragmento,homePerdidos)
//                .addToBackStack(null)
                .commit();



        drawer = findViewById(R.id.drawer_layout);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Home > Perdidos");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer((int) Gravity.LEFT);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_superior,menu);


        bottomBar2 = findViewById(R.id.bottom_toolbar);

        bottomBar = findViewById(R.id.bottom_toolbar);
        Menu bottomMenu = bottomBar2.getMenu();
        getMenuInflater().inflate(R.menu.menu_toolbar_inferior, bottomMenu);
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


}