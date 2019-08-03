package com.example.donateapp;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;


//este es el adaptador sirve de puente para llenar un item(cardview) a un recycler
public class RecyclerViewAdaptador
        extends RecyclerView.Adapter<RecyclerViewAdaptador.ViewHolder>
        implements View.OnClickListener {

    List<Productos> productos;
    private View.OnClickListener listener;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Titulo, Descripcion;
        private ImageView fotoProducto;
        private CircularImageView ImagenUsuario;
        private TextView Username;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Titulo = (TextView) itemView.findViewById(R.id.Titulo);
            Descripcion = (TextView) itemView.findViewById(R.id.descripcion);
            fotoProducto = (ImageView) itemView.findViewById(R.id.fotoProductoLayout);


            ImagenUsuario = (CircularImageView) itemView.findViewById(R.id.ImagenUserItem);
            Username = (TextView) itemView.findViewById(R.id.UserNameItem);


        }


    }





    public RecyclerViewAdaptador(List<Productos> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_prueba, null, false);
        ViewHolder viewHolder = new ViewHolder(view);


        view.setOnClickListener(this);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.Titulo.setText(productos.get(i).getTitulo());
        viewHolder.Descripcion.setText(productos.get(i).getDescripcion());
        viewHolder.Username.setText(productos.get(i).getNombreUsuario());



        String ip = "192.168.0.7:8080";
        String urlImagen = "http://"+ip+"/donateapp/productoImagen/";
        String urlImagen1 = "http://"+ip+"/donateapp/imagenes/";

         Picasso.get()
                .load(urlImagen+(productos.get(i).getFotoProducto()).toString())
                 .resize(800,800)
                 .centerInside()
                 .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.fotoProducto);

        final String finalurl = urlImagen1+(productos.get(i).getImagenUsuario()).toString();
        Picasso.get()
                .load(urlImagen1+(productos.get(i).getImagenUsuario()).toString())
                .resize(800,800)
                .centerInside()
                .placeholder(R.mipmap.ic_launcher)

                .fetch(new Callback() {
                    @Override
                    public void onSuccess() {

                        Picasso.get()
                                .load(finalurl)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                 .into(viewHolder.ImagenUsuario);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });






    }

    @Override
    public int getItemCount() {
        return productos.size();
    }


    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }


    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }



}