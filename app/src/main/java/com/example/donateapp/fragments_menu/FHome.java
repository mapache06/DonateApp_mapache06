package com.example.donateapp.fragments_menu;


import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.donateapp.Productos;
import com.example.donateapp.R;
import com.example.donateapp.RecyclerViewAdaptador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */


//En este fragment se mostraran los productos que esten disponibles
    //para donacion
public class FHome extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    //esta variable FloatingActionButton funcionara para agregar un producto que desees donar
    private FloatingActionButton txt;
    private RecyclerView re_products;





    //con este array se llenaran los productos almacenados en la base de datos
    private ArrayList<Productos> Lista_productos;

    ProgressDialog progress;

    RequestQueue request;
    JsonObjectRequest jsonObject;





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


        //Se asignan las variables a sus controles
        Lista_productos = new ArrayList<>();
        txt = (FloatingActionButton) view.findViewById(R.id.ImagenAdd);

        re_products = (RecyclerView) view.findViewById(R.id.recyclerId);
        re_products.setLayoutManager(new LinearLayoutManager(getContext()));




        cargarWebServices();










        //se llena el fragment con los productos, mosyrrandolos en un CardView/RecyclerView con el metodo llenar lista
       // llenarLista();



//con las 2 lineas de codigo siguiente se le cambia el color al boton flotante
        int color = Color.parseColor("#FFFFFF");
        txt.setColorFilter(color);


        return view;
    }

    private void cargarWebServices() {
        String ip = getString(R.string.ip);
        progress= new ProgressDialog(getContext());
        progress.setMessage("Consultando Productos...");
        progress.show();



        String urlImagen = "http://"+ip+"/donateapp/";
        String url = "http://"+ip+"/donateapp/ConsultarListaProductos.php";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,this,this);
        request = Volley.newRequestQueue(getContext());
        request.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),"No se pudo conectar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
        progress.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
    Productos producto = null;

        JSONArray json = response.optJSONArray("producto");
        try {
            for (int i = 0; i<json.length(); i++){
                 producto = new Productos();
                 JSONObject jsonObject = null;
                 jsonObject = json.getJSONObject(i);

                 producto.setDescripcion(jsonObject.optString("Descripcion"));
                 producto.setFotoProducto(jsonObject.getString("Foto"));
                 producto.setTitulo(jsonObject.getString("Titulo"));


                 Lista_productos.add(producto);

                 progress.hide();
                //Se crea una instancia de la clase adapter que se creo previamente y se llama al constructos que recibe una lista
                RecyclerViewAdaptador adaptador = new RecyclerViewAdaptador(Lista_productos);
                re_products.setAdapter(adaptador);
            }
        } catch (JSONException e) {
            e.printStackTrace();

            Toast.makeText(getContext(),"No se pudo conectar "+response.toString(),Toast.LENGTH_SHORT).show();
            progress.hide();
        }
    }

    //por el momento se esta llenando la lista de manera manual, pero se podra llenar desde la base de datos
    private void llenarLista() {
        /*
        Lista_productos.add(new Productos("Mordecai", "Es un pajaro azul con un pico amarillo" +
                "\n Su mejor amigo es Rigby", R.drawable.mordecai));

        Lista_productos.add(new Productos("Elmo", "Es un muppet rojo con ojos grandes" +
                "\n Su mejor amigo es Rigby", R.drawable.elmo1));

        Lista_productos.add(new Productos("Rigby", "Es un mapache caje con un osico negro" +
                "\n Su mejor amigo es Rigby", R.drawable.rigby));

        Lista_productos.add(new Productos("Esdras", "Es un mapache caje con un osico negro" +
                "\n Su mejor amigo es Rigby", R.drawable.esdrasjpg));
   */
    }



}
