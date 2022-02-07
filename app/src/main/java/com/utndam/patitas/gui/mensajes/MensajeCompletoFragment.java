package com.utndam.patitas.gui.mensajes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.utndam.patitas.R;
import com.utndam.patitas.model.MensajeModel;

public class MensajeCompletoFragment extends Fragment {

    public ImageView remitenteFoto;
    public TextView remitenteNombre;
    public TextView publicacionAsociada;
    public TextView infoContacto;
    public TextView contenido;



    MensajeModel item;


    public MensajeCompletoFragment() {

    }
    public MensajeCompletoFragment(MensajeModel i){
        super();
        item = i;
    }

    public static MensajeCompletoFragment newInstance(String param1, String param2) {
        MensajeCompletoFragment fragment = new MensajeCompletoFragment();
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

        View v =  inflater.inflate(R.layout.fragment_mensaje_completo, container, false);

        remitenteFoto = v.findViewById(R.id.remitente_foto);
        remitenteNombre = v.findViewById(R.id.remitente_nombre);
        publicacionAsociada = v.findViewById(R.id.publicacion_asociada);
        infoContacto = v.findViewById(R.id.info_contacto);
        contenido = v.findViewById(R.id.contenido);


        remitenteFoto.setImageResource(item.remitenteFoto);
        remitenteNombre.setText(item.remitenteNombre);
        publicacionAsociada.setText(item.publicacionAsociada);
        infoContacto.setText(item.contacto);
        contenido.setText(item.contenido);

        return v;
    }

}