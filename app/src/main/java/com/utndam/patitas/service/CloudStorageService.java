package com.utndam.patitas.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.utndam.patitas.gui.home.AltaPublicacionFragment;
import com.utndam.patitas.model.UsuarioModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CloudStorageService {

    FirebaseStorage storage;
    public CloudStorageService(){
        storage = FirebaseStorage.getInstance();

    }
    public interface DestinoSubirPerfil{
        public void recibirUrlNuevo(String url);
    }

    public void setImagen(ImageView imageView, String url, Context context){
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(context)
                .load(storageReference)
                .into(imageView);

        //Glide.with(context /* context */)
         //    .load(storageReference)
          //   .into(imageView);
    }

    public void subirImagenPerfil(String urlExterno,String idUsuario, DestinoSubirPerfil destinoSubirPerfil){
        URL url = null;
        try {
            url = new URL(urlExterno);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        StorageReference ref = storage.getReference()
                .child("images/" + idUsuario + "/" + "profile" + ".jpg");
        InputStream stream = null;
        try {
            stream = url.openConnection().getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadTask uploadTask = ref.putStream(stream);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    //stream.close();
                    destinoSubirPerfil.recibirUrlNuevo(downloadUri.toString());

                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }

    public void subirImagen(AltaPublicacionFragment altaPublicacionFragment, ImageView imageView, String nombreUsuario, String idUsuario){
        // Get the data from an ImageView as bytes
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        StorageReference ref = storage.getReference()
                .child("images/" + idUsuario + "/" + timeStamp + "1" + ".jpg");

        UploadTask uploadTask = ref.putBytes(data);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    altaPublicacionFragment.subirAFirestore(downloadUri.toString());

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
        return;
    }

}
