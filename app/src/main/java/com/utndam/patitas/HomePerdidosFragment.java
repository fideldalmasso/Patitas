package com.utndam.patitas;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;

import com.google.android.material.transition.MaterialFadeThrough;
import com.utndam.patitas.placeholder.PlaceholderContent2;

/**
 * A fragment representing a list of Items.
 */
public class HomePerdidosFragment extends Fragment implements  onCardSelectedListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;



    LayoutInflater inflater;
    ViewGroup container;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomePerdidosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomePerdidosFragment newInstance(int columnCount) {
        HomePerdidosFragment fragment = new HomePerdidosFragment();
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


//        TransitionInflater inflater = TransitionInflater.from(requireContext());
//        setEnterTransition(inflater.inflateTransition(R.transition.fade));

        Transition transition = TransitionInflater.from(requireContext())
                .inflateTransition(R.transition.shared_image);
        setSharedElementEnterTransition(transition);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;


        View view = inflater.inflate(R.layout.fragment_home_perdidos, container, false);

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

            HomePerdidosRecycler adaptador = new HomePerdidosRecycler(PlaceholderContent2.ITEMS2);
            adaptador.setListener(this);
            recyclerView.setAdapter(adaptador);


//            recyclerView.addOnItemTouchListener(context,recyclerView, new Recycler);


        }


        return view;
    }


    @Override
    public void onCardSelectedListener(HomePerdidosRecycler.AnimalCardViewHolder holder) {
//        FragmentCardCompletoBinding  b = new FragmentCardCompletoBinding.inflate(getLayoutInflater());
        CardCompletoFragment frag = new CardCompletoFragment();
//        frag.setImagen(holder.mImagen);

//        findNavController().navigate(R.id.detailAction, null, null, extras)

        getParentFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_out,
                        R.anim.fade_in
                )
                .addSharedElement(holder.mImagen,"transicion_imagen2")
                .addSharedElement(holder.titulo,"transicion_titulo")
                .replace(R.id.listaPosta, frag)
                .addToBackStack(null)
                .commit();
    }
}
