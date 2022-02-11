package com.utndam.patitas.gui.home;

import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.MainActivity;
import com.utndam.patitas.gui.ingreso.AfterTextChangedTextWatcher;
import com.utndam.patitas.gui.mapas.MapsSimpleFragment;
import com.utndam.patitas.model.PublicacionModel;
import com.utndam.patitas.service.CloudFirestoreService;
import com.utndam.patitas.service.CloudStorageService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AltaPublicacionFragment extends Fragment {

//ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this, R.array.country_codes, android.R.layout.simple_spinner_item);

    /*
    val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
(textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
     */
    TextInputLayout tipoPublicacion;
    TextInputLayout tipoAnimal;
    AutoCompleteTextView tipoPublicacionEdit;
    AutoCompleteTextView tipoAnimalEdit;
    TextInputLayout tituloPublicacionInput;
    TextInputLayout descripcionPublicacionInput;
    TextInputEditText tituloPublicacionEdit;
    TextInputEditText descripcionPublicacionEdit;
    FragmentManager fragmentManager;
    MapsSimpleFragment mapaFrag;
    TextView tituloImagen;
    ImageView mImageView;
    TextInputLayout imageHint;
    String pathFoto;
    Button buttonAltaPublicacion;
    private final ActivityResultLauncher<Uri> mTakePicture = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if(result){
                //mImageView.setImageURI(photoURI);
                RequestOptions myOptions = new RequestOptions()
                        .override(dpAPixel(190), dpAPixel(190));
                Glide.with(getContext())
                        .asBitmap()
                        .apply(myOptions)
                        .load(photoURI)
                        .into(mImageView);
            }

        }
    });
    Uri photoURI;
    private final ActivityResultLauncher<String> mPickPicture = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if(result!=null) {

                mImageView.setTag(true);
                RequestOptions myOptions = new RequestOptions()
                        .override(dpAPixel(190), dpAPixel(190));
                Glide.with(getContext())
                        .asBitmap()
                        .apply(myOptions)
                        .load(result)
                        .into(mImageView);

                //mImageView.setImageURI(result);
            }
        }
    });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alta_publicacion, container, false);
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            view.setBackgroundColor(getResources().getColor(R.color.black, getActivity().getTheme()));
        }
        else view.setBackgroundColor(getResources().getColor(R.color.white, getActivity().getTheme()));

        tipoPublicacion = view.findViewById(R.id.menu_publicaciones);
        tipoPublicacionEdit = view.findViewById(R.id.menu_publicaciones_texto);
        tipoAnimal = view.findViewById(R.id.menu_animal);
        tipoAnimalEdit = view.findViewById(R.id.menu_animal_texto);
        tipoPublicacionEdit.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.tipos_de_publicacion,R.layout.support_simple_spinner_dropdown_item));
        tipoAnimalEdit.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.tipos_de_animal,R.layout.support_simple_spinner_dropdown_item));
        tituloPublicacionInput = view.findViewById(R.id.titulo_publicacion_input);
        tituloPublicacionEdit = view.findViewById(R.id.titulo_publicacion_edit);
        descripcionPublicacionInput = view.findViewById(R.id.descripcion_input);
        descripcionPublicacionEdit = view.findViewById(R.id.descripcion_edit);
        tituloImagen = view.findViewById(R.id.titulo_imagen);
        mImageView = view.findViewById(R.id.imageView);
        mImageView.setImageResource(R.drawable.outline_add_photo_alternate_24);
        mImageView.setTag(false);
        imageHint = view.findViewById(R.id.imageHint);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showBottomSheetDialog();
                //((MainActivity)getActivity()).getImagen();
            }
        });


        tipoPublicacionEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            tipoPublicacion.setError(null);
        });
        tipoAnimalEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            tipoAnimal.setError(null);
        });
        tituloPublicacionEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            tituloPublicacionInput.setError(null);
        });
        descripcionPublicacionEdit.addTextChangedListener((AfterTextChangedTextWatcher) e ->{
            descripcionPublicacionInput.setError(null);
        });



        buttonAltaPublicacion = view.findViewById(R.id.boton_alta);
        buttonAltaPublicacion.setTag(this);
        buttonAltaPublicacion.setOnClickListener(new View.OnClickListener() {
            @Override
//            public void onClick(View view) {
//                new CloudStorageService().subirImagen((AltaPublicacionFragment) buttonAltaPublicacion.getTag(),mImageView, "damianlips" ,"abc");
//            }
            public void onClick(View view) {
                boolean todoOk=true;

                if(tipoPublicacionEdit.getText().toString().isEmpty()){
                    tipoPublicacion.setErrorEnabled(true);
                    tipoPublicacion.setError("Selecciona uno de los 4 tipos.");
                    todoOk=false;
                }
                if(tipoAnimalEdit.getText().toString().isEmpty()){
                    tipoAnimal.setErrorEnabled(true);
                    tipoAnimal.setError("Selecciona un tipo de animal.");
                    todoOk=false;
                }

                if(tituloPublicacionEdit.getText().toString().isEmpty()) {
                    tituloPublicacionInput.setErrorEnabled(true);
                    tituloPublicacionInput.setError("El titulo no puede estar vacio.");
                    todoOk=false;
                }
                if(descripcionPublicacionEdit.getText().toString().isEmpty()) {
                    descripcionPublicacionInput.setErrorEnabled(true);
                    descripcionPublicacionInput.setError("La descripcion no puede estar vacia. (en realidad si)");
                    todoOk = false;
                }
                if(mImageView.getTag().equals(false)){
                    imageHint.setError("No ha seleccionado una imagen");
                    tituloImagen.setError("Imagen no seleccionada");
                    todoOk = false;
                }
                else {
                    tituloImagen.setError(null, null);
                    imageHint.setError(null);
                }
                if(todoOk) {
                    new CloudStorageService().subirImagen((AltaPublicacionFragment) buttonAltaPublicacion.getTag(),mImageView, ((MainActivity)getActivity()).usuarioModel.getMail() ,((MainActivity)getActivity()).usuarioModel.getId());
               }
                else {
                    Toast toast =  Toast.makeText(getContext(), "Contiene errores", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapaFrag = new MapsSimpleFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.contenedor_mapa,mapaFrag)
//                .addToBackStack(null)
                .commit();



    }
/*
    public void setImagen(Bitmap bitmap){
        this.mImageView.setImageBitmap(bitmap);
    }
    public void setImagen(Uri uri){
        this.mImageView.setImageURI(uri);
    }

*/
    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(R.layout.card_agregar_imagen);

        LinearLayout galeria = bottomSheetDialog.findViewById(R.id.galeria);
        LinearLayout foto = bottomSheetDialog.findViewById(R.id.foto);
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPickPicture.launch("image/*");
                bottomSheetDialog.dismiss();
            }
        });
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImageFoto();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }
    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, //prefix
                ".jpg", //suffix
                dir //directory
        );
        pathFoto = image.getAbsolutePath();
        return image;
    }


    private void getImageFoto(){
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) { }
        if (photoFile != null) {
            photoURI = FileProvider.getUriForFile(getActivity(),
                    "com.utndam.patitas",
                    photoFile);
            mImageView.setTag(true);
        }
        mTakePicture.launch(photoURI);
    }

    public void subirAFirestore(String url){
        PublicacionModel publicacionModel = new PublicacionModel();
        publicacionModel.setId(null);
        publicacionModel.setTipoPublicacion(tipoPublicacionEdit.getText().toString());
        publicacionModel.setTipoAnimal(tipoAnimalEdit.getText().toString());
        publicacionModel.setpTitulo(tituloPublicacionEdit.getText().toString());
        publicacionModel.setDescripcion(descripcionPublicacionEdit.getText().toString());
        publicacionModel.setUrlImagen(url);
        publicacionModel.setLatitud(mapaFrag.getUbicacionElegida().latitude);
        publicacionModel.setLongitud(mapaFrag.getUbicacionElegida().longitude);

        publicacionModel.setIdUsuario(((MainActivity)getActivity()).usuarioModel.getId());

        publicacionModel.setpImagen(0);
        publicacionModel.setpSecundario(null);
        publicacionModel.setpSoporte(null);

        if(((MainActivity)getActivity()).usuarioModel.getId()==null) {
            Toast.makeText(this.getActivity(), "Usuario invalido", Toast.LENGTH_LONG).show();
        }
        else {
            CloudFirestoreService cloudFirestoreService = new CloudFirestoreService();
            cloudFirestoreService.guardarPublicacion(publicacionModel,this);
        }

    }

    public void notificarExito(){
        Toast.makeText(this.getActivity(), "Publicacion creada", Toast.LENGTH_LONG).show();
    }

    private int dpAPixel(int dp){
        float dip = (float)dp;
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        return (int)px;
    }


}