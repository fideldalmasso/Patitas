package com.utndam.patitas.gui.mensajes;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.utndam.patitas.databinding.FragmentMensajeSimpleBinding;
import com.utndam.patitas.model.MensajeModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MensajeModel}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MensajesRecyclerAdapter extends RecyclerView.Adapter<MensajesRecyclerAdapter.MensajeViewHolder> {

    private final List<MensajeModel> listaMensajes;

    public MensajesRecyclerAdapter(List<MensajeModel> items) {
        listaMensajes = items;
    }

    @Override
    public MensajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MensajeViewHolder(FragmentMensajeSimpleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final MensajeViewHolder holder, int position) {
//        holder.mItem = listaMensajes.get(position);

        holder.senderFoto.setImageResource(listaMensajes.get(position).senderFoto);
        holder.senderNombre.setText(listaMensajes.get(position).senderNombre);
        holder.contenido.setText(listaMensajes.get(position).contenido);
        holder.publicacionAsociada.setText(listaMensajes.get(position).publicacionAsociada);
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }

    public class MensajeViewHolder extends RecyclerView.ViewHolder {
        public final ImageView senderFoto;
        public final TextView senderNombre;
        public final TextView contenido;
        public final TextView publicacionAsociada;


        public MensajeViewHolder(FragmentMensajeSimpleBinding binding) {
            super(binding.getRoot());
            this.senderFoto = binding.senderFoto;
            this.senderNombre = binding.senderNombre;
            this.contenido = binding.contenido;
            this.publicacionAsociada = binding.publicacionAsociada;
        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}