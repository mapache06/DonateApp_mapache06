package com.example.donateapp.fragments_menu;


import android.app.Activity;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.donateapp.Productos;
import com.example.donateapp.R;
import com.example.donateapp.RecyclerViewAdaptador;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FHome extends Fragment {

    private FloatingActionButton txt;
    private RecyclerView re_products;
    private ArrayList<Productos> Lista_productos;
    public FHome() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fhome, container, false);

        // Inflate the layout for this fragment
       // View view1 = inflater.inflate(R.layout.activity_main, container, false);

        Lista_productos = new ArrayList<>();
        txt = (FloatingActionButton) view.findViewById(R.id.ImagenAdd);
        re_products = (RecyclerView) view.findViewById(R.id.recyclerId);
        re_products.setLayoutManager(new LinearLayoutManager(getContext()));

        llenarLista();
        RecyclerViewAdaptador adaptador = new RecyclerViewAdaptador(Lista_productos);
        re_products.setAdapter(adaptador);


        int color = Color.parseColor("#FFFFFF");
        txt.setColorFilter(color);


        return view;
    }

    private void llenarLista() {
        Lista_productos.add(new Productos("Mordecai", "Es un pajaro azul con un pico amarillo" +
                "\n Su mejor amigo es Rigby", R.drawable.mordecai));

        Lista_productos.add(new Productos("Elmo", "Es un muppet rojo con ojos grandes" +
                "\n Su mejor amigo es Rigby", R.drawable.elmo1));

        Lista_productos.add(new Productos("Rigby", "Es un mapache caje con un osico negro" +
                "\n Su mejor amigo es Rigby", R.drawable.rigby));

        Lista_productos.add(new Productos("Esdras", "Es un mapache caje con un osico negro" +
                "\n Su mejor amigo es Rigby", R.drawable.esdrasjpg));
    }





}
