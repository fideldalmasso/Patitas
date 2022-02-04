package com.utndam.patitas.gui.home;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.ingreso.MapsFragmentSimple;

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
    AutoCompleteTextView tipoPublicacion;
    AutoCompleteTextView tipoAnimal;
    FragmentManager fragmentManager;
    MapsFragmentSimple mapaFrag;
    ImageView mImageView;
    String pathFoto;
    private final ActivityResultLauncher<Uri> mTakePicture = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if(result){
                mImageView.setImageURI(photoURI);
            }

        }
    });
    Uri photoURI;
    private final ActivityResultLauncher<String> mPickPicture = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri result) {
            if(result!=null)
                mImageView.setImageURI(result);
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

        tipoPublicacion = view.findViewById(R.id.menu_publicaciones_texto);
        tipoAnimal = view.findViewById(R.id.menu_animal_texto);
        tipoPublicacion.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.tipos_de_publicacion,R.layout.support_simple_spinner_dropdown_item));
        tipoAnimal.setAdapter(ArrayAdapter.createFromResource(requireContext(),R.array.tipos_de_animal,R.layout.support_simple_spinner_dropdown_item));
        mImageView = view.findViewById(R.id.imageView);
        mImageView.setImageResource(R.drawable.outline_add_photo_alternate_24);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
                //((MainActivity)getActivity()).getImagen();
            }
        });


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapaFrag = new MapsFragmentSimple();
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
        }
        mTakePicture.launch(photoURI);
    }




}