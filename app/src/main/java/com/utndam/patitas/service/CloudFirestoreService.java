package com.utndam.patitas.service;

import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.utndam.patitas.gui.MainActivity;
import com.utndam.patitas.gui.busqueda.BusquedaFragment;
import com.utndam.patitas.gui.home.AltaPublicacionFragment;
import com.utndam.patitas.model.PublicacionModel;
import com.utndam.patitas.model.UsuarioModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CloudFirestoreService {

    FirebaseFirestore db;

    public CloudFirestoreService(){
        db = FirebaseFirestore.getInstance();
    }
    public void guardarUsuario(UsuarioModel usuarioModel, Fragment fragment){
        DocumentReference newUserRef = db.collection("usuarios").document();
        newUserRef.set(usuarioModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(fragment.getActivity(), "DocumentSnapshot successfully written!", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(fragment.getActivity(), "Error writing document" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void buscarUsuario(String mail, String tipoCuenta, Fragment fragment){
        db.collection("usuarios")
                .whereEqualTo("tipoCuenta", tipoCuenta)
                .whereEqualTo("mail", mail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size()==0) {
                                Toast.makeText(fragment.getActivity(), "No se encontro usuario", Toast.LENGTH_LONG).show();
                            }
                            else if(task.getResult().size()>1){
                                Toast.makeText(fragment.getActivity(), "Error: demasiados usuarios con el mismo nombre", Toast.LENGTH_LONG).show();
                            }
                            else {
                                //QueryDocumentSnapshot document = task.getResult().getDocuments().get(0);
                                QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                                UsuarioModel usuarioModel = new UsuarioModel();
                                usuarioModel.setId(document.getId());
                                usuarioModel.setFotoUrl(document.getString("fotoUrl"));
                                usuarioModel.setMail(document.getString("mail"));
                                usuarioModel.setTelefono(document.getString("telefono"));
                                usuarioModel.setNombreCompleto(document.getString("nombreCompleto"));
                                usuarioModel.setTipoCuenta(document.getString("tipoCuenta"));
                                Toast.makeText(fragment.getActivity(), usuarioModel.toString(), Toast.LENGTH_LONG).show();
                                //falta retornar usuario
                            }

                           /* for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }*/
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void buscarUsuario(String mail, String tipoCuenta, MainActivity mainActivity){
        db.collection("usuarios")
                .whereEqualTo("tipoCuenta", tipoCuenta)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size()==0) {
                                //Toast.makeText(mainActivity, "No se encontro usuario", Toast.LENGTH_LONG).show();
                                UsuarioModel usuarioModel = new UsuarioModel();
                                FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
                                if(usuario.getPhotoUrl()!=null)usuarioModel.setFotoUrl(usuario.getPhotoUrl().toString());
                                usuarioModel.setMail(usuario.getEmail());
                                if(usuario.getPhoneNumber()!=null)usuarioModel.setTelefono(usuario.getPhoneNumber());
                                if(usuario.getDisplayName()!=null)usuarioModel.setNombreCompleto(usuario.getDisplayName());
                                if(usuario.getProviderId()!=null)usuarioModel.setTipoCuenta(usuario.getProviderId());
                                db.collection("usuarios").add(usuarioModel)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                usuarioModel.setId(documentReference.getId());
                                                mainActivity.setUsuarioModel(usuarioModel);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(mainActivity, "Error: no se pudo registrar usuario", Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                            else if(task.getResult().size()>1){
                                Toast.makeText(mainActivity, "Error: demasiados usuarios con el mismo nombre", Toast.LENGTH_LONG).show();
                            }
                            else {
                                //QueryDocumentSnapshot document = task.getResult().getDocuments().get(0);
                                QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                                UsuarioModel usuarioModel = new UsuarioModel();
                                usuarioModel.setId(document.getId());
                                usuarioModel.setFotoUrl(document.getString("fotoUrl"));
                                usuarioModel.setMail(document.getString("mail"));
                                usuarioModel.setTelefono(document.getString("telefono"));
                                usuarioModel.setNombreCompleto(document.getString("nombreCompleto"));
                                usuarioModel.setTipoCuenta(document.getString("tipoCuenta"));
                                mainActivity.setUsuarioModel(usuarioModel);
                                //Toast.makeText(mainActivity, usuarioModel.toString(), Toast.LENGTH_LONG).show();
                            }

                           /* for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }*/
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void guardarPublicacion(PublicacionModel publicacionModel, AltaPublicacionFragment fragment){
        DocumentReference newPublicationRef = db.collection("publicaciones").document();
        newPublicationRef.set(publicacionModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        //Toast.makeText(fragment.getActivity(), "DocumentSnapshot successfully written!", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Toast.makeText(fragment.getActivity(), "Error writing document" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void buscarPublicaciones(String tipoPublicacion, String tipoAnimal, BusquedaFragment busquedaFragment){
        // Create a reference to the cities collection
        CollectionReference publicacionesRef = db.collection("publicaciones");
        if(tipoPublicacion==null && tipoAnimal==null){
            publicacionesRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<PublicacionModel> lista;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    lista = (ArrayList<PublicacionModel>) task.getResult().getDocuments().stream()
                                            .map( p -> armarPublicacion((QueryDocumentSnapshot) p))
                                            .collect(Collectors.toList());
                                }
                                else{
                                    lista = new ArrayList<PublicacionModel>();
                                    for (QueryDocumentSnapshot p : task.getResult()) {
                                        lista.add(armarPublicacion(p));
                                    }
                                }
                                busquedaFragment.resultadoQuery(lista);

                            } else {
                                //Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        else {
            Query query;
            if(tipoPublicacion!=null){
                query = publicacionesRef.whereEqualTo("tipoPublicacion",tipoPublicacion);
                if(tipoAnimal!=null)
                    query.whereEqualTo("tipoAnimal",tipoAnimal);
            }
            else query = publicacionesRef.whereEqualTo("tipoAnimal",tipoAnimal);

            query.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<PublicacionModel> lista;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            lista = (ArrayList<PublicacionModel>) task.getResult().getDocuments().stream()
                                    .map( p -> armarPublicacion((QueryDocumentSnapshot) p))
                                    .collect(Collectors.toList());
                        }
                        else{
                            lista = new ArrayList<PublicacionModel>();
                            for (QueryDocumentSnapshot p : task.getResult()) {
                                lista.add(armarPublicacion(p));
                            }
                        }
                        busquedaFragment.resultadoQuery(lista);

                    } else {
                        //Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }

        /*
        db.collection("cities")
        .whereEqualTo("capital", true)
        .get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
         */

    }

    private PublicacionModel armarPublicacion(QueryDocumentSnapshot p){
        PublicacionModel publicacionModel = new PublicacionModel();
        publicacionModel.setIdUsuario(p.getString("idUsuario"));
        publicacionModel.setpTitulo(p.getString("pTitulo"));
        publicacionModel.setUrlImagen(p.getString("urlImagen"));
        publicacionModel.setId(p.getId());
        publicacionModel.setDescripcion(p.getString("descripcion"));
        publicacionModel.setTipoAnimal(p.getString("tipoAnimal"));
        publicacionModel.setTipoPublicacion(p.getString("tipoPublicacion"));
        publicacionModel.setFecha(p.getDate("fecha"));
        publicacionModel.setLongitud(p.getDouble("longitud"));
        publicacionModel.setLatitud(p.getDouble("latitud"));
        return publicacionModel;
    }

}
