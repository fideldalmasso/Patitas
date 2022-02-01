package com.utndam.patitas.gui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.appcompat.widget.MenuPopupWindow;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.transition.MaterialFadeThrough;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.MainActivity;
import com.utndam.patitas.gui.ingreso.MapsFragment;

public class AltaPublicacionFragment extends Fragment {

//ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this, R.array.country_codes, android.R.layout.simple_spinner_item);

    /*
    val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
(textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
     */
    AutoCompleteTextView tipoPublicacion;
    AutoCompleteTextView tipoAnimal;
    FragmentManager fragmentManager;
    MapsFragment mapaFrag;
    ImageView mImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alta_publicacion, container, false);

        tipoPublicacion = view.findViewById(R.id.menu_publicaciones_texto);
        tipoAnimal = view.findViewById(R.id.menu_animal_texto);
        tipoPublicacion.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.tipos_de_publicacion,R.layout.support_simple_spinner_dropdown_item));
        tipoAnimal.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.tipos_de_animal,R.layout.support_simple_spinner_dropdown_item));
        mImageView = view.findViewById(R.id.imageView);
        mImageView.setImageResource(R.drawable.outline_add_photo_alternate_24);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).getImagen();

            }
        });

        mapaFrag = new MapsFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_mapa,mapaFrag)
//                .addToBackStack(null)
                .commit();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setImagen(Bitmap bitmap){
        this.mImageView.setImageBitmap(bitmap);
    }


}