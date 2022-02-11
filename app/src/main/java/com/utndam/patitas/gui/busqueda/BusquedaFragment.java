package com.utndam.patitas.gui.busqueda;

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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.ingreso.AfterTextChangedTextWatcher;
import com.utndam.patitas.gui.mapas.MapsSimpleFragment;
import com.utndam.patitas.model.PublicacionModel;

import java.util.ArrayList;
import java.util.List;

public class BusquedaFragment extends Fragment {

    TextInputLayout tipoPublicacion;
    TextInputLayout tipoAnimal;
    AutoCompleteTextView tipoPublicacionEdit;
    AutoCompleteTextView tipoAnimalEdit;
    TextInputLayout tituloPublicacionInput;
    TextInputEditText tituloPublicacionEdit;
    Button buttonBuscar;
    FragmentManager fragmentManager;
    MapsSimpleFragment mapaFrag;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

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



        buttonBuscar = view.findViewById(R.id.boton_buscar);
        buttonBuscar.setTag(this);
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean todoOk=true;
                String animal = tipoAnimalEdit.getText().toString();
                String publicacion = tipoPublicacionEdit.getText().toString();

                if(todoOk) {
                    Toast toast =  Toast.makeText(getContext(), "Buscando", Toast.LENGTH_LONG);
                    toast.show(); }
                else {
                    Toast toast =  Toast.makeText(getContext(), "No busco xd", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        return view;
    }

    public ArrayList<PublicacionModel> resultadoQuery(ArrayList<PublicacionModel> publis) {
        ArrayList<PublicacionModel> aux = new ArrayList<PublicacionModel>();
        return aux;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapaFrag = new MapsSimpleFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_mapa_busqueda,mapaFrag)
//                .addToBackStack(null)
                .commit();
    }


}
