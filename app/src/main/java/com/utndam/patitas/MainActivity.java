package com.utndam.patitas;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    DrawerLayout drawer;
    BottomNavigationView bottomBar;
    FragmentManager fragmentManager;
    HomePerdidosFragment homePerdidos;
    BlankFragment blankFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homePerdidos = new HomePerdidosFragment();
        blankFrag = new BlankFragment();

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

        bottomBar = findViewById(R.id.bottom_toolbar);
        Menu bottomMenu = bottomBar.getMenu();
        getMenuInflater().inflate(R.menu.menu_toolbar_inferior, bottomMenu);
//        for (int i = 0; i < bottomMenu.size(); i++) {
//            bottomMenu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    return onOptionsItemSelected(item);
//                }
//            });
//        }

        bottomBar.setSelectedItemId(R.id.boton_home);
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                                .replace(R.id.contenedor_fragmento,blankFrag)
//                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.boton_ajustes:
                        toolbar.setTitle("Ajustes");
                        fragmentManager.beginTransaction()
                                .replace(R.id.contenedor_fragmento,blankFrag)
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