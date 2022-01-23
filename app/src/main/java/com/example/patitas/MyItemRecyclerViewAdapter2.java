package com.example.patitas;

import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patitas.databinding.FragmentHomePerdidos2Binding;
import com.example.patitas.placeholder.PlaceholderContent2.PlaceholderItem2;
import com.example.patitas.databinding.FragmentHomePerdidosBinding;
import com.example.patitas.placeholder.PlaceholderContent2;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem2}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter2 extends RecyclerView.Adapter<MyItemRecyclerViewAdapter2.ViewHolder> {

    private final List<PlaceholderItem2> mValues;

    public MyItemRecyclerViewAdapter2(List<PlaceholderItem2> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentHomePerdidos2Binding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
        holder.mImagen.setImageResource(mValues.get(position).pImagen);
        holder.titulo.setText(mValues.get(position).pTitulo);
        holder.secundario.setText(mValues.get(position).pSecundario);
        holder.soporte.setText(mValues.get(position).pSoporte);
//        holder.boton1.setText("accion11");
//        holder.boton2.setText("accion22");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImagen;
        public final TextView titulo;
        public final TextView secundario;
        public final TextView soporte;
        public final MaterialButton boton1;
        public final MaterialButton boton2;
        public PlaceholderItem2 mItem;

        public ViewHolder(FragmentHomePerdidos2Binding binding) {
            super(binding.getRoot());
            this.mImagen = binding.cardImagen;
            this.titulo = binding.cardTitulo;
            this.secundario = binding.cardSecundario;
            this.soporte = binding.cardSoporte;
            this.boton1 = binding.cardBoton1;
            this.boton2 = binding.cardBoton2;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText() + "'";
        }
    }

}