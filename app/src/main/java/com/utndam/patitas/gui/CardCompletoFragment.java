package com.utndam.patitas.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.utndam.patitas.R;
import com.utndam.patitas.model.PublicacionModel;

import java.io.File;
import java.io.FileOutputStream;

public class CardCompletoFragment extends Fragment {

    public ImageView imagen;
    public TextView titulo;
    public TextView secundario;
    public TextView soporte;
    public MaterialButton botonShare;
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
        botonShare = v.findViewById(R.id.card_completo_boton1);
        boton2 = v.findViewById(R.id.card_completo_boton2);

        imagen.setImageResource(item.pImagen);
        imagen.setTransitionName("transicion_imagen");
        titulo.setText(item.pTitulo);
        secundario.setText(item.pSecundario);
        soporte.setText(item.pSoporte);

        botonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Now share image function will be called
                // here we  will be passing the text to share
                // Getting drawable value from image
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imagen.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                shareImageandText(bitmap);
            }
        });

        return v;
    }
    private void shareImageandText(Bitmap bitmap) {
        Uri uri = getImageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding text to share
        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image");

        // Add subject Here
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");

        // setting type to image
        intent.setType("image/png");

        // calling startactivity() to share
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    // Retrieving the url to share
    private Uri getImageToShare(Bitmap bitmap) {
        File imagefolder = new File(getContext().getCacheDir(), "images");
        Uri uri = null;
        try {
            imagefolder.mkdirs();
//            File file = new File(imagefolder, "shared_image.png");
            File file = new File(imagefolder, "imagen_patitas.jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(this.getContext(), "com.utndam.patitas", file);
        } catch (Exception e) {
            Toast.makeText(this.getContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return uri;
    }
}