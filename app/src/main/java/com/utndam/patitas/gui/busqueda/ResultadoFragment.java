package com.utndam.patitas.gui.busqueda;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialFadeThrough;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.MainActivity;
import com.utndam.patitas.gui.home.AltaPublicacionFragment;
import com.utndam.patitas.gui.home.HomeRecyclerAdapter;
import com.utndam.patitas.gui.home.ListaEjemploPublicaciones;
import com.utndam.patitas.gui.home.PublicacionCompletaFragment;
import com.utndam.patitas.gui.home.onCardSelectedListener;
import com.utndam.patitas.model.PublicacionModel;
import com.utndam.patitas.service.CloudStorageService;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class ResultadoFragment extends Fragment implements onCardSelectedListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;



    LayoutInflater inflater;
    ViewGroup container;
    ArrayList<PublicacionModel> lista = new ArrayList<PublicacionModel>();



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ResultadoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ResultadoFragment newInstance(int columnCount) {
        ResultadoFragment fragment = new ResultadoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setEnterTransition(new MaterialFadeThrough());
        setExitTransition(new MaterialFadeThrough());

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity)getActivity()).cambiarTextoBarraSuperior("Resultado");
        this.inflater = inflater;
        this.container = container;

        View view = inflater.inflate(R.layout.fragment_resultado, container, false);
        // Set the adapter
        View view2 = view.findViewById(R.id.lista_resultado);
        if (view2 instanceof RecyclerView) {
            Context context = view2.getContext();
            RecyclerView recyclerView = (RecyclerView) view2;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ListaResultado holderContent = new ListaResultado(lista);

//            HomeRecyclerAdapter adaptador = new HomeRecyclerAdapter(holderContent.getItems());
            ResultadoRecyclerAdapter adaptador = new ResultadoRecyclerAdapter(holderContent.getItems(), getContext());
            adaptador.setListener(this);
            recyclerView.setAdapter(adaptador);


        }


        return view;
    }



    @Override
    public void onCardSelectedListener(PublicacionModel item) {

        PublicacionCompletaFragment frag = new PublicacionCompletaFragment(item);

        getParentFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                )
                .replace(R.id.listaPosta_resultado, frag)
                .addToBackStack(null)
                .commit();

    }

    public void setLista(ArrayList<PublicacionModel> porUbicacion) {
        lista.clear();
        for(PublicacionModel p : porUbicacion) lista.add(p);

    }
}

