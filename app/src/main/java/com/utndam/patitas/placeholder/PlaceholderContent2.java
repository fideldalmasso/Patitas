package com.utndam.patitas.placeholder;

import com.utndam.patitas.R;

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
public class PlaceholderContent2 {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PlaceholderItem2> ITEMS2 = new ArrayList<PlaceholderItem2>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PlaceholderItem2> ITEM_MAP = new HashMap<String, PlaceholderItem2>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createPlaceholderItem(i));
        }
    }

    private static void addItem(PlaceholderItem2 item) {
        ITEMS2.add(item);
        ITEM_MAP.put(item.pTitulo, item);
    }

    private static PlaceholderItem2 createPlaceholderItem(int position) {
        return new PlaceholderItem2(R.drawable.gato3, "Titulo"+String.valueOf(position), "secundario","soporte");
    }


    /**
     * A placeholder item representing a piece of content.
     */
    public static class PlaceholderItem2 {
        public final int pImagen;
        public final String pTitulo;
        public final String pSecundario;
        public final String pSoporte;

        public PlaceholderItem2(int imagen, String titulo, String secundario, String soporte) {
            this.pImagen = imagen;
            this.pTitulo = titulo;
            this.pSecundario = secundario;
            this.pSoporte = soporte;
        }

        @Override
        public String toString() {
            return pSecundario;
        }
    }
}