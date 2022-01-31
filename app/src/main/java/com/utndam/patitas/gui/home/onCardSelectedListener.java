package com.utndam.patitas.gui.home;

import com.utndam.patitas.databinding.FragmentCardSimpleBinding;
import com.utndam.patitas.model.PublicacionModel;

public interface onCardSelectedListener {
    void onCardSelectedListener(HomePerdidosRecycler.AnimalCardViewHolder holder,
                                PublicacionModel item,
                                FragmentCardSimpleBinding binding);
}
