package com.example.donateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductoDetails extends AppCompatActivity {

    private Productos producto = new Productos();
    private TextView titulo, descripcion, categoria, condicion, horariosDeRecoleccion;
    private ImageView imagenProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_details);

        titulo = (TextView) findViewById(R.id.TituloProducto);
        descripcion = (TextView) findViewById(R.id.DescripcionProducto);
        categoria = (TextView) findViewById(R.id.Categoria);
        condicion = (TextView) findViewById(R.id.Condicion);
        horariosDeRecoleccion = (TextView) findViewById(R.id.AmSpinner);
        imagenProducto = (ImageView) findViewById(R.id.ImagenProducto);





        //se valida si se mandaron elementos y los recibe
        Bundle extras = getIntent().getExtras();
        if(extras.getParcelable("producto")!= null) {
            producto = extras.getParcelable("producto");

        }

        if(producto.getTitulo() != null){
            titulo.setText(producto.getTitulo().toString());
            descripcion.setText(producto.getDescripcion().toString());
            categoria.setText(producto.getCategoría().toString());
            condicion.setText(producto.getCondicion().toString());
            horariosDeRecoleccion.setText(producto.getHorariosDeRecoleccion().toString());

            String ip = "192.168.0.7:8080";
            String urlImagen = "http://"+ip+"/donateapp/productoImagen/";

            Picasso.get()
                    .load(urlImagen+(producto.getFotoProducto()).toString())
                    .resize(800,800)
                    .centerInside()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imagenProducto);
        }



    }
}
