package com.utndam.patitas.gui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.utndam.patitas.R;
import com.utndam.patitas.model.PublicacionModel;

public class CardCompletoFragment extends Fragment {

    public ImageView imagen;
    public TextView titulo;
    public TextView secundario;
    public TextView soporte;
    public MaterialButton boton1;
    public MaterialButton boton2;



    PublicacionModel item;


    public CardCompletoFragment() {

    }
    public CardCompletoFragment(PublicacionModel i){
        super();
        item = i;
    }

    public static CardCompletoFragment newInstance(String param1, String param2) {
        CardCompletoFragment fragment = new CardCompletoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        TransitionInflater inflater = TransitionInflater.from(requireContext());
//        setEnterTransition(inflater.inflateTransition(R.transition.fade));

//
//        Transition transition = TransitionInflater.from(requireContext())
//                .inflateTransition(R.transition.shared_image);
//        setSharedElementEnterTransition(transition);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        iv.setImageResource(id_imagen);
//        imagen.setTransitionName("transicion_imagen");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_card_completo, container, false);

        imagen = v.findViewById(R.id.card_completo_imagen);
        titulo = v.findViewById(R.id.card_completo_titulo);
        secundario = v.findViewById(R.id.card_completo_secundario);
        soporte = v.findViewById(R.id.card_completo_soporte);
        boton1 = v.findViewById(R.id.card_completo_boton1);
        boton2 = v.findViewById(R.id.card_completo_boton2);

        imagen.setImageResource(item.pImagen);
        imagen.setTransitionName("transicion_imagen");
        titulo.setText(item.pTitulo);
        secundario.setText(item.pSecundario);
        soporte.setText(item.pSoporte);

        return v;
    }
}