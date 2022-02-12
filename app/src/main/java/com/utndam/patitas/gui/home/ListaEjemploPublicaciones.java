package com.utndam.patitas.gui.home;

import com.utndam.patitas.R;
import com.utndam.patitas.model.PublicacionModel;

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
public class ListaEjemploPublicaciones {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PublicacionModel> ITEMS = new ArrayList<PublicacionModel>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PublicacionModel> ITEM_MAP = new HashMap<String, PublicacionModel>();

    private static final int COUNT = 25;

    public ListaEjemploPublicaciones() {
        if(ITEMS.isEmpty()){
            for (int i = 1; i <= COUNT; i++) {
                addItem(createPlaceholderItem(i));
            }
        }
    }


    private void addItem(PublicacionModel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getpTitulo(), item);
    }

    private PublicacionModel createPlaceholderItem(int position) {
        return new PublicacionModel(position%2==0? R.drawable.gato3 : R.drawable.perro1, "Titulo"+String.valueOf(position), "secundario","soporte");
    }

    public List<PublicacionModel> getItems() {
        return ITEMS;
    }


    /**
     * A placeholder item representing a piece of content.
     */
//    public class PlaceholderItem2 {
//        public final int pImagen;
//        public final String pTitulo;
//        public final String pSecundario;
//        public final String pSoporte;
//
//        public PlaceholderItem2(int imagen, String titulo, String secundario, String soporte) {
//            this.pImagen = imagen;
//            this.pTitulo = titulo;
//            this.pSecundario = secundario;
//            this.pSoporte = soporte;
//        }
//
//        @Override
//        public String toString() {
//            return pSecundario;
//        }
//    }
}