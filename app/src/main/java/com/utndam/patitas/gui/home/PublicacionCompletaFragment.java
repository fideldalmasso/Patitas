package com.utndam.patitas.gui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.utndam.patitas.R;
import com.utndam.patitas.gui.MainActivity;
import com.utndam.patitas.gui.mapas.MapsSimpleFragment;
import com.utndam.patitas.model.MensajeModel;
import com.utndam.patitas.model.PublicacionModel;
import com.utndam.patitas.model.UsuarioActual;
import com.utndam.patitas.service.CloudFirestoreService;
import com.utndam.patitas.service.CloudStorageService;

import java.io.File;
import java.io.FileOutputStream;

public class PublicacionCompletaFragment extends Fragment {

    public ImageView imagen;
    public TextView titulo;
    public TextView descripcion;
    public TextView infoContacto;
    public MaterialButton botonShare;
    public MaterialButton boton2;
    private ExtendedFloatingActionButton floatingActionButton;
    private MapsSimpleFragment mapaFrag;
    private FragmentManager fragmentManager;
    private CloudFirestoreService service = new CloudFirestoreService();
    private CloudStorageService service2 = new CloudStorageService();

    PublicacionModel item;


    public PublicacionCompletaFragment() {

    }
    public PublicacionCompletaFragment(PublicacionModel i){
        super();
        item = i;
    }

    public static PublicacionCompletaFragment newInstance(String param1, String param2) {
        PublicacionCompletaFragment fragment = new PublicacionCompletaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapaFrag = new MapsSimpleFragment();
        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.card_contenedor_mapa,mapaFrag)
                .commit();

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
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity)getActivity()).cambiarTextoBarraSuperior("Home");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((MainActivity)getActivity()).cambiarTextoBarraSuperior(item.getTitulo());
        View v =  inflater.inflate(R.layout.fragment_publicacion_completa, container, false);

        imagen = v.findViewById(R.id.card_completo_imagen);
        titulo = v.findViewById(R.id.card_completo_titulo);
        descripcion = v.findViewById(R.id.card_completo_descripcion);
        infoContacto = v.findViewById(R.id.card_completo_info_contacto);
        botonShare = v.findViewById(R.id.card_completo_boton1);

        floatingActionButton = v.findViewById(R.id.fab2);
        floatingActionButton.setTag(this);

        floatingActionButton.setOnClickListener(view ->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

            final EditText input = new EditText(getContext());
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
//            input.setLines(3);
//            input.setMaxLines(6);
            input.setSingleLine(false);
            input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(120)});
//            input.setVerticalScrollBarEnabled(true);
//            input.setMovementMethod(ScrollingMovementMethod.getInstance());
//            input.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);;

            builder
//                    .setCancelable(false)
                    .setTitle("Responder a \""+item.getTitulo()+"\"")
                    .setView(input)
                    .setPositiveButton("Aceptar", (dialog, i) -> {
//                        dialog.dismiss();
                        Toast.makeText(getContext(), "Falta implementar guardar el mensaje en DB", Toast.LENGTH_LONG).show();
                        MensajeModel m = new MensajeModel();
                        m.setPublicacionAsociada(item.getTitulo());
                        m.setIdPublicacionAsociada(item.getId());
                        m.setIdReceptor(item.getIdUsuario());
                        m.setIdRemitente(UsuarioActual.getInstance().getId());
                        m.setContenido(input.getText().toString());
                        service.guardarMensaje(m);

                    })
                    .setNegativeButton("Cancelar", (dialog, id) -> {
//                        dialog.dismiss();
                        dialog.cancel();
                    })
                    .create()
                    .show();
        });

//        floatingActionButton.setOnLongClickListener(view -> {
//            UsuarioModel usuarioModel= new UsuarioModel();
//            usuarioModel.setTipoCuenta("Google");
//            usuarioModel.setId(null);
//            usuarioModel.setNombreCompleto("Juan Perez");
//            usuarioModel.setTelefono("4222222");
//            usuarioModel.setMail("juanperez@gmail.com");
//            usuarioModel.setFotoUrl(null);
//            CloudFirestoreService cloudFirestoreService = new CloudFirestoreService();
//            cloudFirestoreService.guardarUsuario(usuarioModel, (Fragment) floatingActionButton.getTag());
//            return false;
//        });


        boton2 = v.findViewById(R.id.card_completo_boton2);

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloudFirestoreService cloudFirestoreService = new CloudFirestoreService();
                cloudFirestoreService.buscarUsuario("juanperez@gmail.com","Google", (Fragment) floatingActionButton.getTag());
            }
        });

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


//        imagen.setImageBitmap(item.getBitmap());
        service2.setImagen(imagen, item.getUrlImagen(),imagen.getContext());
        titulo.setText(item.getTitulo());
        descripcion.setText(item.getDescripcion());
        infoContacto.setText(item.getInfoContacto());

        return v;
    }
    private void shareImageandText(Bitmap bitmap) {
        Uri uri = getImageToShare(bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);

        // putting uri of image to be shared
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // adding text to share
        intent.putExtra(Intent.EXTRA_TEXT, titulo.getText() + " \nDescarga Patitas en el siguiente link: ");

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