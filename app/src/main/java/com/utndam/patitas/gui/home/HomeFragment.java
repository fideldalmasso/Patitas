package com.utndam.patitas.gui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialFadeThrough;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.CardCompletoFragment;
import com.utndam.patitas.model.PublicacionModel;

/**
 * A fragment representing a list of Items.
 */
public class HomeFragment extends Fragment implements onCardSelectedListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;



    LayoutInflater inflater;
    ViewGroup container;
    FloatingActionButton floatingActionButton;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeFragment newInstance(int columnCount) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
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

        this.inflater = inflater;
        this.container = container;

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        floatingActionButton = view.findViewById(R.id.fab);

        AltaPublicacionFragment frag = new AltaPublicacionFragment();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getParentFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in,
                                R.anim.fade_out,
                                R.anim.fade_in,
                                R.anim.slide_out
                        )
                        .replace(R.id.listaPosta,frag)
                        .addToBackStack(null)
                        .commit();
            }
        });


        // Set the adapter
        View view2 = view.findViewById(R.id.listxd);
        if (view2 instanceof RecyclerView) {
            Context context = view2.getContext();
            RecyclerView recyclerView = (RecyclerView) view2;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            ListaEjemploPublicaciones holderContent = new ListaEjemploPublicaciones();

            HomeRecyclerAdapter adaptador = new HomeRecyclerAdapter(holderContent.getItems());
            adaptador.setListener(this);
            recyclerView.setAdapter(adaptador);


        }


        return view;
    }


    @Override
    public void onCardSelectedListener(PublicacionModel item) {

        CardCompletoFragment frag = new CardCompletoFragment(item);

        getParentFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                       R.anim.slide_in,
                       R.anim.fade_out,
                       R.anim.fade_in,
                       R.anim.slide_out
                )
                .replace(R.id.listaPosta, frag)
                .addToBackStack(null)
                .commit();

    }
}
