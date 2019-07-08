package com.example.donateapp;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdaptador extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
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
        viewHolder.fotoProducto.setImageResource(productos.get(i).getFotoProducto());
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }
}