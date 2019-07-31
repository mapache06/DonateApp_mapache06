package com.example.donateapp;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


//este es el adaptador sirve de puente para llenar un item(cardview) a un recycler
public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Titulo, Descripcion;
        private ImageView fotoProducto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = (TextView) itemView.findViewById(R.id.Titulo);
            Descripcion = (TextView) itemView.findViewById(R.id.descripcion);
            fotoProducto = (ImageView) itemView.findViewById(R.id.fotoProductoLayout);


        }


    }



    public List<Productos> productos;

    public RecyclerViewAdaptador(List<Productos> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_prueba, null, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.Titulo.setText(productos.get(i).getTitulo());
        viewHolder.Descripcion.setText(productos.get(i).getDescripcion());
        String ip = "192.168.0.7:8080";
        String urlImagen = "http://"+ip+"/donateapp/productoImagen/";

         Picasso.get()
                .load(urlImagen+(productos.get(i).getFotoProducto()).toString())
                 .resize(800,800)
                 .centerInside()
                 .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.fotoProducto);
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }





}