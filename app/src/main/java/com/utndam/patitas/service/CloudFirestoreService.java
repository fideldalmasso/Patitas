package com.utndam.patitas.service;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.utndam.patitas.model.UsuarioModel;

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

}
