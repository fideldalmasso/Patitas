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
        ITEM_MAP.put(item.getTitulo(), item);
    }

    private PublicacionModel createPlaceholderItem(int position) {
        PublicacionModel p = new PublicacionModel();
        p.setLatitud(-31.623018753813945);
        p.setLongitud(-60.693003602891345);
        p.setTitulo("Publicación número "+position);
        p.setTipoPublicacion("perro");
        p.setDescripcion("Perro perdido en zona centro, ofrecemos recompensa");
        p.setImagen(position%2==0? R.drawable.perro1 : R.drawable.gato3);
        p.setInfoContacto("Teléfono: +54342111222\nBarrio Centro");
        p.setUrlImagen("https://firebasestorage.googleapis.com/v0/b/patitas2-f92e8.appspot.com/o/images%2Fj7iI7Epc1YNdn9DVSRpe%2F20220212_0652481.jpg?alt=media&token=36440deb-3809-4a21-ae34-1fea2a918d29");
        return p;
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