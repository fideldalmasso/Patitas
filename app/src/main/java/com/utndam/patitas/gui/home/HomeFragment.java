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
import com.utndam.patitas.gui.MainActivity;
import com.utndam.patitas.model.PublicacionModel;
import com.utndam.patitas.service.CloudFirestoreService;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class HomeFragment extends Fragment implements onCardSelectedListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;



    private LayoutInflater inflater;
    private ViewGroup container;
    private FloatingActionButton floatingActionButton;
    private HomeRecyclerAdapter adaptador;
    private List<PublicacionModel> lista;



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
        ((MainActivity)getActivity()).cambiarTextoBarraSuperior("Home");
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

//            ListaEjemploPublicaciones holderContent = new ListaEjemploPublicaciones();
//            HomeRecyclerAdapter adaptador = new HomeRecyclerAdapter(holderContent.getItems());

            lista = new ArrayList<PublicacionModel>();
            adaptador = new HomeRecyclerAdapter(lista);
            adaptador.setListener(this);
            recyclerView.setAdapter(adaptador);

            new CloudFirestoreService().buscarPublicaciones(null, null, new CloudFirestoreService.DestinoQueryPublicaciones() {
                @Override
                public void recibirPublicaciones(List<PublicacionModel> listaResultado) {

                    lista.addAll(listaResultado);
                    adaptador.notifyDataSetChanged();
//                    //descargar imagenes
//                    new Thread(() -> {
//                        for(PublicacionModel p : lista) {
//                            Bitmap bitmap = null;
//                            try {
//                                StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(p.getUrlImagen());
//                                bitmap = GlideApp.with(getContext()).asBitmap().load(storageReference).submit().get();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            p.setBitmap(bitmap);
//                        }
//
//                        //actualizar la ui
//                        getActivity().runOnUiThread(() -> {
//                            adaptador.notifyDataSetChanged();
//                        });
//
//                    }).start();

                }
            });
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
                .replace(R.id.listaPosta, frag)
                .addToBackStack(null)
                .commit();

    }
}
