package com.utndam.patitas.gui.mensajes;

import com.utndam.patitas.R;
import com.utndam.patitas.model.MensajeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ListaEjemploMensajes {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<MensajeModel> ITEMS = new ArrayList<MensajeModel>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, MensajeModel> ITEM_MAP = new HashMap<String, MensajeModel>();

    private static final int COUNT = 25;

    private static final int foto = R.drawable.ic_baseline_person_24;
    private static final String nombre = "Juan Pablo Cobol";
    private static final String infoContacto = "Teléfono: +54342111222\nBarrio Centro";
    private static final String contenido = "Hola, lo vi entre San Martin y Suipacha y lo agarré. Quedó en mi laburo. Está muy asustado, vení cuanto antes. Me encuentro entre 8 y 18hs en el local informático de la esquina. Llamame cualquier cosa.";
    private static final String publicacion = "Publicación: Firulais";


    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(MensajeModel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.remitenteNombre +item.publicacionAsociada, item);
    }

    private static MensajeModel createPlaceholderItem(int position) {
        return new MensajeModel(foto,nombre,contenido,publicacion,infoContacto);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }


}