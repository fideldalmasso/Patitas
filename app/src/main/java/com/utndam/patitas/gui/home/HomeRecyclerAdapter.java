package com.utndam.patitas.gui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.utndam.patitas.databinding.FragmentPublicacionSimpleBinding;
import com.utndam.patitas.model.PublicacionModel;
import com.utndam.patitas.service.CloudStorageService;

import java.text.DecimalFormat;
import java.util.List;


public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.PublicacionViewHolder> {

    private final List<PublicacionModel> listaPublicaciones;
    private onCardSelectedListener listener;
    private CloudStorageService service = new CloudStorageService();

    public HomeRecyclerAdapter(List<PublicacionModel> items) {
        listaPublicaciones = items;
    }

    @Override
    public PublicacionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PublicacionViewHolder(FragmentPublicacionSimpleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final PublicacionViewHolder holder, int position) {
//        holder.imagen.setImageBitmap(listaPublicaciones.get(position).getBitmap());
//        holder.imagen.setImageResource(listaPublicaciones.get(position).getImagen());
        service.setImagen(holder.imagen, listaPublicaciones.get(position).getUrlImagen(),holder.imagen.getContext());
        holder.titulo.setText(listaPublicaciones.get(position).getTitulo());
        holder.distancia.setText(new DecimalFormat("##.##").format(listaPublicaciones.get(position).getDistancia()) + " km");

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