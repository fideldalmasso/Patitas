package com.utndam.patitas.gui.busqueda;

import static java.lang.Math.abs;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.MainActivity;
import com.utndam.patitas.gui.home.AltaPublicacionFragment;
import com.utndam.patitas.gui.home.HomeRecyclerAdapter;
import com.utndam.patitas.gui.home.ListaEjemploPublicaciones;
import com.utndam.patitas.gui.home.onCardSelectedListener;
import com.utndam.patitas.gui.ingreso.AfterTextChangedTextWatcher;
import com.utndam.patitas.gui.mapas.MapsSimpleFragment;
import com.utndam.patitas.model.PublicacionModel;
import com.utndam.patitas.model.UsuarioActual;
import com.utndam.patitas.service.CloudFirestoreService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BusquedaFragment extends Fragment  {

    TextInputLayout tipoPublicacion;
    TextInputLayout tipoAnimal;
    AutoCompleteTextView tipoPublicacionEdit;
    AutoCompleteTextView tipoAnimalEdit;
    TextInputLayout tituloPublicacionInput;
    TextInputEditText tituloPublicacionEdit;
    Button buttonBuscar;
    FragmentManager fragmentManager;
    MapsSimpleFragment mapaFrag;
    LayoutInflater inflater;
    ViewGroup container;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;

        View view = inflater.inflate(R.layout.fragment_busqueda, container, false);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            view.setBackgroundColor(getResources().getColor(R.color.black, getActivity().getTheme()));
        }
        else view.setBackgroundColor(getResources().getColor(R.color.white, getActivity().getTheme()));

        tipoPublicacion = view.findViewById(R.id.menu_publicaciones_busqueda);
        tipoPublicacionEdit = view.findViewById(R.id.menu_publicaciones_texto_busqueda);
        tipoAnimal = view.findViewById(R.id.menu_animal_busqueda);
        tipoAnimalEdit = view.findViewById(R.id.menu_animal_texto_busqueda);
        tipoPublicacionEdit.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.tipos_de_publicacion,R.layout.support_simple_spinner_dropdown_item));
        tipoAnimalEdit.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.tipos_de_animal,R.layout.support_simple_spinner_dropdown_item));
//        tituloPublicacionInput = view.findViewById(R.id.titulo_publicacion_input_busqueda);
//        tituloPublicacionEdit = view.findViewById(R.id.titulo_publicacion_edit_busqueda);

        tipoPublicacionEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            tipoPublicacion.setError(null);
        });
        tipoAnimalEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            tipoAnimal.setError(null);
        });
//        tituloPublicacionEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
//            tituloPublicacionInput.setError(null);
//        });

        ResultadoFragment frag = new ResultadoFragment();
        buttonBuscar = view.findViewById(R.id.boton_buscar);
        buttonBuscar.setTag(this);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean todoOk=true;
                String animal = tipoAnimalEdit.getText().toString();
                String publicacion = tipoPublicacionEdit.getText().toString();
                if(animal.isEmpty()) animal=null;
                if(publicacion.isEmpty()) publicacion=null;
                ArrayList<PublicacionModel> publis = new ArrayList<PublicacionModel>();
                ArrayList<PublicacionModel> porUbicacion;
                FragmentManager fmanager = getParentFragmentManager();

                CloudFirestoreService cloudFirestoreService = new CloudFirestoreService();
                cloudFirestoreService.buscarPublicaciones(publicacion, animal, new CloudFirestoreService.DestinoQueryPublicaciones() {
                    @Override
                    public void recibirPublicaciones(List<PublicacionModel> listaResultado){
                        for(PublicacionModel p: listaResultado) publis.add(p);
                        LatLng ubicacionActual = UsuarioActual.getInstance().getUbicacionActual();
                        frag.setLista(filtrar(publis, ubicacionActual, 50000));

                        fmanager.beginTransaction()
                                .setCustomAnimations(
                                        R.anim.slide_in,
                                        R.anim.fade_out,
                                        R.anim.fade_in,
                                        R.anim.slide_out
                                )
                                .replace(R.id.ubicacion, frag)
                                .addToBackStack(null)
                                .commit();
                    }
                });

            }
        });



        return view;
    }
    private ArrayList<PublicacionModel> filtrar(List<PublicacionModel> publis, LatLng ubic, long dif) {
        ArrayList<PublicacionModel> ret = new ArrayList<PublicacionModel>();
        for(PublicacionModel p:publis){
            float [] dist2 = new float[1];
            Location.distanceBetween(ubic.latitude,ubic.longitude,p.getLatitud(),p.getLongitud(),dist2); //guarda la distancia en metros en dist[0]
            float dist = dist2[0];
            dist = (dist != 0) ? (dist / 1000) : 0; //pasar a km
            p.setDistancia(dist);
            if(dist<= dif ){
                ret.add(p);
            }
        }
        Collections.sort(ret, new Comparator<PublicacionModel>(){

            @Override
            public int compare(PublicacionModel p1, PublicacionModel p2) {
                if(p1.getDistancia() < p2.getDistancia()) return -1;
                else if(p1.getDistancia() < p2.getDistancia()) return 1;
                return 0;
            }
        });

        return ret;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapaFrag = new MapsSimpleFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_mapa_busqueda,mapaFrag)
                .commit();
    }

}
