package com.utndam.patitas.gui.mensajes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utndam.patitas.R;
import com.utndam.patitas.model.MensajeModel;
import com.utndam.patitas.model.UsuarioActual;
import com.utndam.patitas.service.CloudFirestoreService;

import java.util.ArrayList;
import java.util.List;


public class MisMensajesFragment extends Fragment  implements onMensajeSelectedListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private CloudFirestoreService service = new CloudFirestoreService();
    private List<MensajeModel> listaMensajes;
    private MisMensajesRecyclerAdapter adaptador;

    public MisMensajesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MisMensajesFragment newInstance(int columnCount) {
        MisMensajesFragment fragment = new MisMensajesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaMensajes = new ArrayList<>();

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_mensajes, container, false);

        // Set the adapter
        View view2 = view.findViewById(R.id.lista_mensajes_xd);
        if (view2 instanceof RecyclerView) {
            Context context = view2.getContext();
            RecyclerView recyclerView = (RecyclerView) view2;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

//            MisMensajesRecyclerAdapter adaptador = new MisMensajesRecyclerAdapter(ListaEjemploMensajes.ITEMS);
            adaptador = new MisMensajesRecyclerAdapter(listaMensajes);
            adaptador.setListener(this);
            recyclerView.setAdapter(adaptador);
            service.buscarMensajesPorUsuario(UsuarioActual.getInstance().getId(), new CloudFirestoreService.DestinoQueryMensajes() {
                @Override
                public void recibirMensajes(List<MensajeModel> listaResultado) {
                    listaMensajes.addAll(listaResultado);
                    adaptador.notifyDataSetChanged();
                }
            });

        }
        return view;
    }

    @Override
    public void onMensajeSelected(MensajeModel item) {

        MensajeCompletoFragment frag = new MensajeCompletoFragment(item);

        getParentFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,
                        R.anim.fade_out,
                        R.anim.fade_in,
                        R.anim.slide_out
                )
                .replace(R.id.listaPosta2, frag)
                .addToBackStack(null)
                .commit();

    }

}