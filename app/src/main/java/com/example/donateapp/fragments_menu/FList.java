package com.example.donateapp.fragments_menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.donateapp.InsertarProducto;
import com.example.donateapp.Persona;
import com.example.donateapp.ProductoDetails;
import com.example.donateapp.Productos;
import com.example.donateapp.R;
import com.example.donateapp.RecyclerViewAdaptador;
import com.example.donateapp.RecyclerViewAdaptadorLista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FList extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    Persona obj = new Persona();
    RecyclerView re_Lista;
    Productos producto;
    Productos producto12 = new Productos();
    //con este array se llenaran los productos almacenados en la base de datos
    private ArrayList<Productos> Lista_productos;

    //Se crea una variable que ayudarra a generar una barra de progreso
    ProgressDialog progress;

    //Se crean  variables de tipo Json que nos ayuda a recibir informacion
    RequestQueue request;
    JsonObjectRequest jsonObject;

    public FList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_flist, container, false);

        //Se valida si se mando del activity anterior un objeto de ser así, lo recibe
        if (getArguments().getParcelable("x") != null) {
            obj = getArguments().getParcelable("x");
        }

        Lista_productos = new ArrayList<>();
        re_Lista = (RecyclerView) v.findViewById(R.id.Recycler_Lista);

        re_Lista.setLayoutManager(new LinearLayoutManager(getContext()));


        //Se manda a llmar el metodo que recibe los datos de los productos
        cargarWebServices();

        return  v;
    }

    private void cargarWebServices() {
        //Se crea esta variable que contiene la ip del servidor
        String ip = getString(R.string.ip);

        //Se crea una barra de progreso que indique al usuario que esta consultando
        progress= new ProgressDialog(getContext());
        progress.setMessage("Consultando Productos...");
        progress.show();

        //Se crea string con la url donde invoca al webservice
        String url = "http://"+ip+"/donateapp/ConsultarListaProductos.php?Id="+obj.id;


        //Se recibe la informacion en forma de Json
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,this,this);
        request = Volley.newRequestQueue(getContext());
        request.add(jsonObjectRequest);
    }


    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("producto");
        try {
            for (int i = 0; i<json.length(); i++){

                producto = new Productos();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);


                //Se asigna las variables de la clase producto con la key
                //que es el nombre de la coliumna de la base de datos
                producto.setDescripcion(jsonObject.optString("Descripcion"));
                producto.setFotoProducto(jsonObject.getString("Foto"));
                producto.setTitulo(jsonObject.getString("Titulo"));
                producto.setAltitud(jsonObject.getString("Altitud"));
                producto.setLatitud(jsonObject.getString("Latitud"));
                producto.setCategoría(jsonObject.getString("categoria"));
                producto.setSituacion(jsonObject.getString("situacion"));
                producto.setHorariosDeRecoleccion(jsonObject.getString("HorariosDeRecoleccion"));
                producto.setCondicion(jsonObject.getString("condicion"));
                producto.setId(jsonObject.getInt("Id"));
                producto.setIdUsuario(jsonObject.getInt("IdUsuario"));
                producto.setNombreUsuario(jsonObject.getString("NombreUsuario"));
                producto.setImagenUsuario(jsonObject.getString("RutaImagen"));



                Lista_productos.add(producto);

                ///Se cierra la barra de progreso
                progress.hide();


                //Se crea una instancia de la clase adapter que se creo previamente y se llama al constructos que recibe una lista
                RecyclerViewAdaptadorLista adaptador = new RecyclerViewAdaptadorLista(Lista_productos);

                //Se le asigna un evento de click al Recycler
                adaptador.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Intent o = new Intent(getContext(), InsertarProducto.class);

                        producto12.setTitulo(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getTitulo());
                        producto12.setHorariosDeRecoleccion(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getHorariosDeRecoleccion());
                        producto12.setSituacion(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getSituacion());
                        producto12.setCategoría(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getCategoría());
                        producto12.setLatitud(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getLatitud());
                        producto12.setAltitud(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getAltitud());
                        producto12.setFotoProducto(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getFotoProducto());
                        producto12.setDescripcion(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getDescripcion());
                        producto12.setCondicion(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getCondicion());
                        producto12.setId(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getId());

                        producto12.setImagenUsuario(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getImagenUsuario());
                        producto12.setIdUsuario(obj.id);
                        producto12.setNombreUsuario(Lista_productos.get(re_Lista.getChildAdapterPosition(v)).getNombreUsuario());






                        o.putExtra("producto", producto12);
                      //  o.putExtra("persona", obj);

                        startActivity(o);
                    }
                });


                re_Lista.setAdapter(adaptador);
            }
        } catch (JSONException e) {
            e.printStackTrace();

            //Si no conecta se manda un Toast con el error
            Toast.makeText(getContext(),"No se pudo conectar "+e.toString(),Toast.LENGTH_SHORT).show();
            progress.hide();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No se pudo conectar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
        progress.hide();
    }
}
