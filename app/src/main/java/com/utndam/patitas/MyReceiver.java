package com.utndam.patitas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.utndam.patitas.gui.MainActivity;

public class MyReceiver extends BroadcastReceiver {
    public static final String EVENTO_PUBLICACION_CREADA="com.utndam.patitas.EVENTO_PUBLICACION_CREADA";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

        switch (intent.getAction()){
            case EVENTO_PUBLICACION_CREADA:
                Toast.makeText(context, "Publicacion " +intent.getExtras().getString("tituloPublicacion") + " creada exitosamente", Toast.LENGTH_LONG).show();
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                                .setSmallIcon(R.drawable.logo_patitas_foreground)
                                .setContentTitle("Publicación creada exitosamente")
                                .setContentText( "Publicacion " +intent.getExtras().getString("tituloPublicacion") + " creada exitosamente")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true);

                NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(context.getApplicationContext());
                notificationManager.notify(0,mBuilder.build());

                break;
        }


    }

}