package com.utndam.patitas.service;

import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import com.utndam.patitas.MyReceiver;
import com.utndam.patitas.gui.MainActivity;
import com.utndam.patitas.gui.home.AltaPublicacionFragment;
import com.utndam.patitas.model.MensajeModel;
import com.utndam.patitas.model.PublicacionModel;
import com.utndam.patitas.model.UsuarioActual;
import com.utndam.patitas.model.UsuarioModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class CloudFirestoreService {

    FirebaseFirestore db;

    public interface DestinoQueryPublicaciones{
        public void recibirPublicaciones(List<PublicacionModel> listaResultado );
    }
    public interface DestinoQueryMensajes{
        public void recibirMensajes(List<MensajeModel> listaResultado );
    }
    public interface DestinoQueryUsuario{
        public void recibirUsuario(UsuarioModel usuarioModel );
    }

    public CloudFirestoreService(){
        db = FirebaseFirestore.getInstance();
    }
    public void actualizarUsuario(UsuarioModel usuarioModel, Fragment fragment){
        DocumentReference newUserRef = db.collection("usuarios").document(usuarioModel.getId());
        newUserRef.set(usuarioModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(fragment.getActivity(), "Datos actualizados exitosamente", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(fragment.getActivity(), "No se pudo actualizar los datos" , Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void buscarUsuario(String id, DestinoQueryUsuario destinoQueryUsuario){
        db.collection("usuarios")
                .document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                destinoQueryUsuario.recibirUsuario(armarUsuario(document));
                            } else {
                                // Log.d(TAG, "No such document");
                            }
                        } else {
                            //Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

    }


/*
    public void buscarUsuario(String mail, Fragment fragment){
        db.collection("usuarios")
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
                                Toast.makeText(fragment.getActivity(), usuarioModel.toString(), Toast.LENGTH_LONG).show();
                                //falta retornar usuario
                            }


                        } else {
                        }
                    }
                });
    }
*/

    public void buscarUsuario(String mail, MainActivity mainActivity){
        db.collection("usuarios")
                .whereEqualTo("mail", mail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().size()==0) {
                                //Toast.makeText(mainActivity, "No se encontro usuario", Toast.LENGTH_LONG).show();
                                UsuarioModel usuarioModel = new UsuarioModel();
                                FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
                                //if(usuario.getPhotoUrl()!=null)usuarioModel.setFotoUrl(usuario.getPhotoUrl().toString());
                                usuarioModel.setMail(usuario.getEmail());
                                if(UsuarioActual.getInstance().getTelefono()!=null)usuarioModel.setTelefono(UsuarioActual.getInstance().getTelefono());
                                if(usuario.getDisplayName()!=null)usuarioModel.setNombreCompleto(usuario.getDisplayName());
                                if(usuario.getPhotoUrl()!=null){
                                    new CloudStorageService().subirImagenPerfil(usuario.getPhotoUrl().toString(), usuario.getUid(),mainActivity, new CloudStorageService.DestinoSubirPerfil() {
                                        @Override
                                        public void recibirUrlNuevo(String url) {
                                            usuarioModel.setFotoUrl(url);
                                            db.collection("usuarios")
                                                    .document(usuario.getUid())
                                                    .set(usuarioModel)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            usuarioModel.setId(usuario.getUid());
                                                            UsuarioActual.getInstance().copiar(usuarioModel);
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                }
                                            })
                                            ;

                                        }
                                    });

                                }
                                else {
                                    usuarioModel.setFotoUrl("gs://patitas2-f92e8.appspot.com/images/default_profile.jpg");
                                    db.collection("usuarios")
                                            .document(usuario.getUid())
                                            .set(usuarioModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    usuarioModel.setId(usuario.getUid());
                                                    UsuarioActual.getInstance().copiar(usuarioModel);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                        }
                                    })
                                    ;
                                }

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
//                                mainActivity.setUsuarioModel(usuarioModel);
                                UsuarioActual.getInstance().copiar(usuarioModel); // ----> es preferible usar este Singleton
                                //Toast.makeText(mainActivity, usuarioModel.toString(), Toast.LENGTH_LONG).show();
                            }

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
                        //fragment.notificarExito();
                        Intent i = new Intent();
                        i.putExtra("tituloPublicacion",publicacionModel.getTitulo());
                        i.setAction(MyReceiver.EVENTO_PUBLICACION_CREADA);
                        LocalBroadcastManager.getInstance(fragment.getActivity()).sendBroadcast(i);

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

    public void guardarMensaje(MensajeModel mensajeModel){
        DocumentReference newMensajeRef = db.collection("mensajes").document();
        newMensajeRef.set(mensajeModel)
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

    public void buscarMensajesPorUsuario(String idReceptor, DestinoQueryMensajes destinoQueryMensajes){
        CollectionReference mensajesRef = db.collection("mensajes");
        mensajesRef.whereEqualTo("idReceptor",idReceptor).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            ArrayList<MensajeModel> lista;
                            HashSet<String> userIds = new HashSet<String>();
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                lista = (ArrayList<MensajeModel>) task.getResult().getDocuments().stream()
                                        .map( p -> armarMensaje((QueryDocumentSnapshot) p))
                                        .collect(Collectors.toList());
                                lista.stream()
                                        .forEach(p -> userIds.add(p.getIdRemitente()));
                            }
                            else{
                                lista = new ArrayList<MensajeModel>();
                                for (QueryDocumentSnapshot p : task.getResult()) {
                                    MensajeModel mensajeModel = armarMensaje(p);
                                    userIds.add(mensajeModel.getIdRemitente());
                                    lista.add(mensajeModel);
                                }
                            }
                            HashMap<String,UsuarioModel> userToInfo = new HashMap<String,UsuarioModel>();
                            db.runTransaction(new Transaction.Function<List<UsuarioModel>>(){
                                @Override
                                public List<UsuarioModel> apply(Transaction transaction) throws FirebaseFirestoreException {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                        return userIds.stream()
                                                .map(u -> {
                                                            try {
                                                                return armarUsuario(
                                                                        transaction.get(db.collection("usuarios").document(u))
                                                                );
                                                            } catch (FirebaseFirestoreException e) {
                                                                e.printStackTrace();
                                                                return null;
                                                            }
                                                        }
                                                )
                                                .collect(Collectors.toList());
                                    }
                                    else {
                                        ArrayList<UsuarioModel> listaUsuarios = new ArrayList<UsuarioModel>();
                                        for(String usu : userIds){
                                            listaUsuarios.add(armarUsuario(
                                                    transaction.get(db.collection("usuarios").document(usu))
                                                    )
                                            );
                                        }
                                        return  listaUsuarios;
                                    }
                                }

                            })
                                    .addOnSuccessListener(new OnSuccessListener<List<UsuarioModel>>() {
                                        @Override
                                        public void onSuccess(List<UsuarioModel> result){
                                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                                result.stream()
                                                        .forEach(u -> userToInfo.put(u.getId(),u));
                                                lista.stream()
                                                        .forEach(m -> {
                                                            m.setRemitenteFotoUrl( userToInfo.get(m.getIdRemitente()).getFotoUrl() );
                                                            m.setRemitenteNombre(userToInfo.get(m.getIdRemitente()).getNombreCompleto());
                                                            if(!userToInfo.get(m.getIdRemitente()).getTelefono().isEmpty())
                                                                m.setContacto("Telefono: " + userToInfo.get(m.getIdRemitente()).getTelefono());
                                                            else m.setContacto("");
                                                        });
                                            }
                                            else {
                                                for(UsuarioModel u : result){
                                                    userToInfo.put(u.getId(),u);
                                                }
                                                for(MensajeModel m : lista){
                                                    m.setRemitenteFotoUrl( userToInfo.get(m.getIdRemitente()).getFotoUrl() );
                                                    m.setRemitenteNombre(userToInfo.get(m.getIdRemitente()).getNombreCompleto());
                                                    if(!userToInfo.get(m.getIdRemitente()).getTelefono().isEmpty())
                                                        m.setContacto("Telefono: " + userToInfo.get(m.getIdRemitente()).getTelefono());
                                                    else m.setContacto("");
                                                }
                                            }
                                            destinoQueryMensajes.recibirMensajes(lista);
                                        }

                                    });

                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });
    }




    public void buscarPublicaciones(String tipoPublicacion, String tipoAnimal, DestinoQueryPublicaciones destinoQueryPublicaciones){
        // Create a reference to the cities collection
        CollectionReference publicacionesRef = db.collection("publicaciones");
        if(tipoPublicacion==null && tipoAnimal==null){
            publicacionesRef.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                ArrayList<PublicacionModel> lista;
                                HashSet<String> userIds = new HashSet<String>();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    lista = (ArrayList<PublicacionModel>) task.getResult().getDocuments().stream()
                                            .map( p -> armarPublicacion((QueryDocumentSnapshot) p))
                                            .collect(Collectors.toList());
                                    lista.stream()
                                            .forEach(p -> userIds.add(p.getIdUsuario()));
                                }
                                else{
                                    lista = new ArrayList<PublicacionModel>();
                                    for (QueryDocumentSnapshot p : task.getResult()) {
                                        PublicacionModel publicacionModel = armarPublicacion(p);
                                        userIds.add(publicacionModel.getIdUsuario());
                                        lista.add(publicacionModel);
                                    }
                                }
                                HashMap<String,String> userToInfo = new HashMap<String,String>();
                                db.runTransaction(new Transaction.Function<List<UsuarioModel>>(){
                                    @Override
                                    public List<UsuarioModel> apply(Transaction transaction) throws FirebaseFirestoreException {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                            return userIds.stream()
                                                    .map(u -> {
                                                                try {
                                                                    return armarUsuario(
                                                                            transaction.get(db.collection("usuarios").document(u))
                                                                    );
                                                                } catch (FirebaseFirestoreException e) {
                                                                    e.printStackTrace();
                                                                    return null;
                                                                }
                                                            }
                                                    )
                                                    .collect(Collectors.toList());
                                        }
                                        else {
                                            ArrayList<UsuarioModel> listaUsuarios = new ArrayList<UsuarioModel>();
                                            for(String usu : userIds){
                                                listaUsuarios.add(armarUsuario(
                                                        transaction.get(db.collection("usuarios").document(usu))
                                                )
                                                );
                                            }
                                            return  listaUsuarios;
                                        }
                                    }

                                })
                                .addOnSuccessListener(new OnSuccessListener<List<UsuarioModel>>() {
                                    @Override
                                    public void onSuccess(List<UsuarioModel> result){
                                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                            result.stream()
                                                    .forEach(u -> userToInfo.put(u.getId(),u.getInfoContacto()));
                                            lista.stream()
                                                    .forEach(p -> p.setInfoContacto(userToInfo.get(p.getIdUsuario())));
                                        }
                                        else {
                                            for(UsuarioModel u : result){
                                                userToInfo.put(u.getId(),u.getInfoContacto());
                                            }
                                            for(PublicacionModel p : lista){
                                                p.setInfoContacto(userToInfo.get(p.getIdUsuario()));
                                            }
                                        }
                                        destinoQueryPublicaciones.recibirPublicaciones(lista);
                                    }

                                });

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
                    query = query.whereEqualTo("tipoAnimal",tipoAnimal);
            }
            else query = publicacionesRef.whereEqualTo("tipoAnimal",tipoAnimal);

            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        ArrayList<PublicacionModel> lista;
                        HashSet<String> userIds = new HashSet<String>();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            lista = (ArrayList<PublicacionModel>) task.getResult().getDocuments().stream()
                                    .map( p -> armarPublicacion((QueryDocumentSnapshot) p))
                                    .collect(Collectors.toList());
                            lista.stream()
                                    .forEach(p -> userIds.add(p.getIdUsuario()));
                        }
                        else{
                            lista = new ArrayList<PublicacionModel>();
                            for (QueryDocumentSnapshot p : task.getResult()) {
                                PublicacionModel publicacionModel = armarPublicacion(p);
                                userIds.add(publicacionModel.getIdUsuario());
                                lista.add(publicacionModel);
                            }
                        }
                        HashMap<String,String> userToInfo = new HashMap<String,String>();
                        db.runTransaction(new Transaction.Function<List<UsuarioModel>>(){
                            @Override
                            public List<UsuarioModel> apply(Transaction transaction) throws FirebaseFirestoreException {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                    return userIds.stream()
                                            .map(u -> {
                                                        try {
                                                            return armarUsuario(
                                                                    transaction.get(db.collection("usuarios").document(u))
                                                            );
                                                        } catch (FirebaseFirestoreException e) {
                                                            e.printStackTrace();
                                                            return null;
                                                        }
                                                    }
                                            )
                                            .collect(Collectors.toList());
                                }
                                else {
                                    ArrayList<UsuarioModel> listaUsuarios = new ArrayList<UsuarioModel>();
                                    for(String usu : userIds){
                                        listaUsuarios.add(armarUsuario(
                                                transaction.get(db.collection("usuarios").document(usu))
                                                )
                                        );
                                    }
                                    return  listaUsuarios;
                                }
                            }

                        })
                                .addOnSuccessListener(new OnSuccessListener<List<UsuarioModel>>() {
                                    @Override
                                    public void onSuccess(List<UsuarioModel> result){
                                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                            result.stream()
                                                    .forEach(u -> userToInfo.put(u.getId(),u.getInfoContacto()));
                                            lista.stream()
                                                    .forEach(p -> p.setInfoContacto(userToInfo.get(p.getIdUsuario())));
                                        }
                                        else {
                                            for(UsuarioModel u : result){
                                                userToInfo.put(u.getId(),u.getInfoContacto());
                                            }
                                            for(PublicacionModel p : lista){
                                                p.setInfoContacto(userToInfo.get(p.getIdUsuario()));
                                            }
                                        }
                                        destinoQueryPublicaciones.recibirPublicaciones(lista);
                                    }

                                });

                    } else {
                        //Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }



    }


    public void buscarPublicacionesPorUsuario(String idUsuario, DestinoQueryPublicaciones destinoQueryPublicaciones){
        // Create a reference to the cities collection
        CollectionReference publicacionesRef = db.collection("publicaciones");

        publicacionesRef.whereEqualTo("idUsuario",idUsuario)
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

                        destinoQueryPublicaciones.recibirPublicaciones(lista);

                    } else {
                        //Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }



    private PublicacionModel armarPublicacion(QueryDocumentSnapshot p){
        PublicacionModel publicacionModel = new PublicacionModel();
        publicacionModel.setIdUsuario(p.getString("idUsuario"));
        String titulo = p.getString("titulo");
        if(titulo==null)
            titulo=p.getString("pTitulo");
        publicacionModel.setTitulo(titulo);
        publicacionModel.setUrlImagen(p.getString("urlImagen"));
        publicacionModel.setId(p.getId());
        publicacionModel.setDescripcion(p.getString("descripcion"));
        publicacionModel.setTipoAnimal(p.getString("tipoAnimal"));
        publicacionModel.setTipoPublicacion(p.getString("tipoPublicacion"));
        publicacionModel.setFecha(p.getDate("fecha"));
        publicacionModel.setLongitud(p.getDouble("longitud"));
        publicacionModel.setLatitud(p.getDouble("latitud"));

        //aca falta que setearle a publicacionModel los atributos que faltan: imagen(int o bitmap), infoContacto(string)

//        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(publicacionModel.getUrlImagen());




        return publicacionModel;
    }
    private UsuarioModel armarUsuario(DocumentSnapshot document){
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setId(document.getId());
        usuarioModel.setFotoUrl(document.getString("fotoUrl"));
        usuarioModel.setMail(document.getString("mail"));
        usuarioModel.setTelefono(document.getString("telefono"));
        usuarioModel.setNombreCompleto(document.getString("nombreCompleto"));
        return  usuarioModel;
    }

    private MensajeModel armarMensaje(QueryDocumentSnapshot m){
        MensajeModel mensajeModel = new MensajeModel();
        mensajeModel.setPublicacionAsociada(m.getString("publicacionAsociada"));
        mensajeModel.setIdPublicacionAsociada(m.getString("idPublicacionAsociada"));
        mensajeModel.setContenido(m.getString("contenido"));
        //mensajeModel.setContacto(m.getString("contacto"));
        mensajeModel.setIdReceptor(m.getString("idReceptor"));
        mensajeModel.setIdRemitente(m.getString("idRemitente"));

        return mensajeModel;
    }

}
