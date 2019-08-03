package com.example.donateapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdaptadorLista
        extends RecyclerView.Adapter<RecyclerViewAdaptadorLista.ViewHolder>
        implements View.OnClickListener

{

    List<Productos> productos;
    private View.OnClickListener listener;

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Titulo;
        private ImageView fotoProducto;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Titulo = (TextView) itemView.findViewById(R.id.TituloProductoLista);
            fotoProducto = (ImageView) itemView.findViewById(R.id.ImagenProductoLista);



        }
    }

    public RecyclerViewAdaptadorLista(List<Productos> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public RecyclerViewAdaptadorLista.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_lista, null, false);
        RecyclerViewAdaptadorLista.ViewHolder viewHolder = new RecyclerViewAdaptadorLista.ViewHolder(view);

        view.setOnClickListener(this);

        return viewHolder;
    }



    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdaptadorLista.ViewHolder viewHolder, int i) {
        viewHolder.Titulo.setText(productos.get(i).getTitulo());


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

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }
}
