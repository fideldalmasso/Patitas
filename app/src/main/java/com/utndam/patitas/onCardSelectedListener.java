package com.utndam.patitas;

import com.utndam.patitas.databinding.FragmentCardSimpleBinding;
import com.utndam.patitas.placeholder.PlaceholderContent2;

public interface onCardSelectedListener {
    void onCardSelectedListener(HomePerdidosRecycler.AnimalCardViewHolder holder,
                                PlaceholderContent2.PlaceholderItem2 item,
                                FragmentCardSimpleBinding binding);
}
