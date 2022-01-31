package com.utndam.patitas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.utndam.patitas.databinding.FragmentCardSimpleBinding;
import com.utndam.patitas.placeholder.PlaceholderContent2.PlaceholderItem2;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem2}.
 * TODO: Replace the implementation with code for your data type.
 */
public class HomePerdidosRecycler extends RecyclerView.Adapter<HomePerdidosRecycler.AnimalCardViewHolder> {

    private final List<PlaceholderItem2> mValues;
    onCardSelectedListener listener;

    public HomePerdidosRecycler(List<PlaceholderItem2> items) {
        mValues = items;
    }

    @Override
    public AnimalCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        return new AnimalCardViewHolder(FragmentCardSimpleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final AnimalCardViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
        holder.mImagen.setImageResource(mValues.get(position).pImagen);
        holder.titulo.setText(mValues.get(position).pTitulo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
//                    Publicacion p = new Publicacion(holder.mItem.pTitulo, holder.mItem.pSecundario, holder.mItem.pImagen);
                    listener.onCardSelectedListener(holder);
                }
            }
        });

//        holder.secundario.setText(mValues.get(position).pSecundario);
//        holder.soporte.setText(mValues.get(position).pSoporte);
//        holder.boton1.setText("accion11");
//        holder.boton2.setText("accion22");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setListener(onCardSelectedListener listener) {
        this.listener = listener;
    }

    public class AnimalCardViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImagen;
        public final TextView titulo;
//        public final TextView secundario;
//        public final TextView soporte;
//        public final MaterialButton boton1;
//        public final MaterialButton boton2;
        public PlaceholderItem2 mItem;

        public AnimalCardViewHolder(FragmentCardSimpleBinding binding) {
            super(binding.getRoot());
            this.mImagen = binding.cardImagen;
            this.titulo = binding.cardTitulo;

//            this.secundario = binding.cardSecundario;
//            this.soporte = binding.cardSoporte;
//            this.boton1 = binding.cardBoton1;
//            this.boton2 = binding.cardBoton2;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText() + "'";
        }
    }

}