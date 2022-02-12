package com.utndam.patitas.gui.busqueda;

import com.utndam.patitas.R;
import com.utndam.patitas.model.PublicacionModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaResultado {

    /**
     * An array of sample (placeholder) items.
     */
    public static final List<PublicacionModel> ITEMS = new ArrayList<PublicacionModel>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public static final Map<String, PublicacionModel> ITEM_MAP = new HashMap<String, PublicacionModel>();


    public ListaResultado(ArrayList<PublicacionModel> lista) {
        ITEMS.clear();
        for(PublicacionModel p : lista){
            addItem(p);
        }
    }


    private void addItem(PublicacionModel item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getpTitulo(), item);
    }


    public List<PublicacionModel> getItems() {
        return ITEMS;
    }


    /**
     * A placeholder item representing a piece of content.
     */
}
