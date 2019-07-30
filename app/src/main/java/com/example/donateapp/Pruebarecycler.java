package com.example.donateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


//en esta clase se creara el recycler que trae los productos
public class Pruebarecycler extends AppCompatActivity {
    private RecyclerView recyclerProductos;
    private RecyclerViewAdaptador adaptadorProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebarecycler);

        recyclerProductos = (RecyclerView) findViewById(R.id.recyclerPrueba);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(this));

        adaptadorProducto = new RecyclerViewAdaptador(obtenerProducto());
        recyclerProductos.setAdapter(adaptadorProducto);
    }

    //este metodo recibe una lista con los productos, por el momento se llenan manual
    public List<Productos> obtenerProducto(){
        List<Productos> productos = new ArrayList<>();
        /*
        productos.add(new Productos("Mordecai", "Es un pajaro azul con un pico amarillo" +
                 "\n Su mejor amigo es Rigby", R.drawable.mordecai));

        productos.add(new Productos("Elmo", "Es un muppet rojo con ojos grandes" +
                "\n Su mejor amigo es Rigby", R.drawable.elmo1));

         productos.add(new Productos("Rigby", "Es un mapache caje con un osico negro" +
                 "\n Su mejor amigo es Rigby", R.drawable.rigby));

        productos.add(new Productos("Esdras", "Es un mapache caje con un osico negro" +
                "\n Su mejor amigo es Rigby", R.drawable.esdrasjpg));

*/
        return productos;
    }

}
