package com.utndam.patitas.gui.mensajes;

import android.view.LayoutInflater;
import android.view.View;
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
public class MisMensajesRecyclerAdapter extends RecyclerView.Adapter<MisMensajesRecyclerAdapter.MensajeViewHolder> {

    private final List<MensajeModel> listaMensajes;

    public MisMensajesRecyclerAdapter(List<MensajeModel> items) {
        listaMensajes = items;
    }

    private onMensajeSelectedListener listener;

    @Override
    public MensajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MensajeViewHolder(FragmentMensajeSimpleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    public void setListener(onMensajeSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(final MensajeViewHolder holder, int position) {
//        holder.mItem = listaMensajes.get(position);

        holder.senderFoto.setImageResource(listaMensajes.get(position).remitenteFoto);
        holder.senderNombre.setText(listaMensajes.get(position).remitenteNombre);
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
            this.senderFoto = binding.remitenteFoto;
            this.senderNombre = binding.remitenteNombre;
            this.contenido = binding.contenido;
            this.publicacionAsociada = binding.publicacionAsociada;

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onMensajeSelected(listaMensajes.get(getAdapterPosition()));
                    }
                }
            });

        }

        @Override
        public String toString() {
            return super.toString() ;
        }
    }
}