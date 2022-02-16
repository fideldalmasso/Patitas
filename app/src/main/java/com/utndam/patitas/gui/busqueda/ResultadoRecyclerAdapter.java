package com.utndam.patitas.gui.busqueda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import com.utndam.patitas.databinding.FragmentPublicacionSimpleBinding;
import com.utndam.patitas.gui.home.onCardSelectedListener;
import com.utndam.patitas.model.PublicacionModel;
import com.utndam.patitas.service.CloudStorageService;

import java.text.DecimalFormat;
import java.util.List;


public class ResultadoRecyclerAdapter extends RecyclerView.Adapter<ResultadoRecyclerAdapter.PublicacionViewHolder> {

    private final List<PublicacionModel> listaPublicaciones;
    private onCardSelectedListener listener;
    private Context contextNow;

    public ResultadoRecyclerAdapter (List<PublicacionModel> items, Context context) {
        listaPublicaciones = items;
        contextNow = context;
    }

    @Override
    public PublicacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PublicacionViewHolder(FragmentPublicacionSimpleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final PublicacionViewHolder holder, int position) {
        CloudStorageService css = new CloudStorageService();
        css.setImagen(holder.imagen, listaPublicaciones.get(position).getUrlImagen(), contextNow);
        holder.titulo.setText(listaPublicaciones.get(position).getTitulo());
        DecimalFormat df = new DecimalFormat("###.##");
        holder.distancia.setText(df.format(listaPublicaciones.get(position).getDistancia()) + " km\n" + listaPublicaciones.get(position).getTipoPublicacion());
    }

    @Override
    public int getItemCount() {
        return listaPublicaciones.size();
    }

    public void setListener(onCardSelectedListener listener) {
        this.listener = listener;
    }


    public class PublicacionViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imagen;
        public final TextView titulo;
        public final TextView distancia;

        public PublicacionViewHolder(FragmentPublicacionSimpleBinding binding) {
            super(binding.getRoot());

            this.imagen = binding.cardImagen;
            this.titulo = binding.cardTitulo;
            this.distancia = binding.cardDistancia;

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        listener.onCardSelectedListener(listaPublicaciones.get(getAdapterPosition()));
                    }
                }
            });

        }

        @Override
        public String toString() {
            return super.toString() + " '" + titulo.getText().toString() + "'";
        }
    }

}