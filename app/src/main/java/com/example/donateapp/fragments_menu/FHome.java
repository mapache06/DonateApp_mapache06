package com.example.donateapp.fragments_menu;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

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
import com.example.donateapp.InsertarProducto;
import com.example.donateapp.Main2Activity;
import com.example.donateapp.Persona;
import com.example.donateapp.ProductoDetails;
import com.example.donateapp.Productos;
import com.example.donateapp.R;
import com.example.donateapp.RecyclerViewAdaptador;
import com.example.donateapp.RegisterNombre;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


//En este fragment se mostraran los productos que esten disponibles
    //para donacion
public class FHome extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener {

    //esta variable FloatingActionButton funcionara para agregar un producto que desees donar

    private RecyclerView re_products;
    public Productos producto12 = new Productos();
    Productos producto = null;
    Persona obj = new Persona();
    private int flag = 0;

    //con este array se llenaran los productos almacenados en la base de datos
    private ArrayList<Productos> Lista_productos;

    //Se crea una variable que ayudarra a generar una barra de progreso
    ProgressDialog progress;

    //Se crean  variables de tipo Json que nos ayuda a recibir informacion
    RequestQueue request;
    JsonObjectRequest jsonObject;

    //Constructor vacío
    public FHome() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fhome, container, false);


        //Se valida si se mando del activity anterior un objeto de ser así, lo recibe
        if (getArguments().getParcelable("x") != null) {
            obj = getArguments().getParcelable("x");
        }



        //Se asignan las variables a sus controles
        Lista_productos = new ArrayList<>();
        final FloatingActionButton txt = (FloatingActionButton) view.findViewById(R.id.ImagenAdd);
        final FloatingActionButton txt2 = (FloatingActionButton) view.findViewById(R.id.ImagenLocation);
        final FloatingActionsMenu menu = (FloatingActionsMenu) view.findViewById(R.id.grupoFab);
        re_products = (RecyclerView) view.findViewById(R.id.recyclerId);
        re_products.setLayoutManager(new LinearLayoutManager(getContext()));

        //Se manda a llmar el metodo que recibe los datos de los productos
        cargarWebServices();

/*
        //con las 2 lineas de codigo siguiente se le cambia el color al boton flotante
        int color = Color.parseColor("#FFFFFF");
        txt.setColorFilter(color);

*/
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), InsertarProducto.class);
                i.putExtra("persona",obj);
                menu.collapse();
                startActivity(i);
            }
        });

        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                cargarWebServices();
                menu.collapse();

            }
        });

        return view;
    }





    private void cargarWebServices() {
        //Se crea esta variable que contiene la ip del servidor
        String ip = getString(R.string.ip);

        //Se crea una barra de progreso que indique al usuario que esta consultando
        progress= new ProgressDialog(getContext());
        progress.setMessage("Consultando Productos...");
        progress.show();

        if(flag == 0) {
            //Se crea string con la url donde invoca al webservice
            String url = "http://" + ip + "/donateapp/ConsultarProductos.php";

            //Se recibe la informacion en forma de Json
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,this,this);
            request = Volley.newRequestQueue(getContext());
            request.add(jsonObjectRequest);
        }
        else
        {
            String url = "http://" + ip + "/donateapp/ConsultarListaProductosByUbicacion.php?Latitud="+String.valueOf(obj.Latitud)+"&"
                    +"Longuitud="+String.valueOf(obj.Altitud);

            //Se recibe la informacion en forma de Json
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,this,this);
            request = Volley.newRequestQueue(getContext());
            request.add(jsonObjectRequest);

        }



    }

    //Si ocurre un error se crea un Toast con los detalles
    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(),"No se pudo conectar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
        progress.hide();
    }

    //Si se realiza la consulta de manera correcta se obtienen todos los elementos que trae
    @Override
    public void onResponse(JSONObject response) {


        JSONArray json = response.optJSONArray("producto");
        try {
            for (int i = 0; i<json.length(); i++){

                producto = new Productos();
                 JSONObject jsonObject = null;
                 jsonObject = json.getJSONObject(i);


                 if(jsonObject.optInt("Id") != 0) {
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
                     RecyclerViewAdaptador adaptador = new RecyclerViewAdaptador(Lista_productos);

                     flag = 0;

                     //Se le asigna un evento de click al Recycler
                     adaptador.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             final Intent o = new Intent(getContext(), ProductoDetails.class);

                             producto12.setTitulo(Lista_productos.get(re_products.getChildAdapterPosition(v)).getTitulo());
                             producto12.setHorariosDeRecoleccion(Lista_productos.get(re_products.getChildAdapterPosition(v)).getHorariosDeRecoleccion());
                             producto12.setSituacion(Lista_productos.get(re_products.getChildAdapterPosition(v)).getSituacion());
                             producto12.setCategoría(Lista_productos.get(re_products.getChildAdapterPosition(v)).getCategoría());
                             producto12.setLatitud(Lista_productos.get(re_products.getChildAdapterPosition(v)).getLatitud());
                             producto12.setAltitud(Lista_productos.get(re_products.getChildAdapterPosition(v)).getAltitud());
                             producto12.setFotoProducto(Lista_productos.get(re_products.getChildAdapterPosition(v)).getFotoProducto());
                             producto12.setDescripcion(Lista_productos.get(re_products.getChildAdapterPosition(v)).getDescripcion());
                             producto12.setCondicion(Lista_productos.get(re_products.getChildAdapterPosition(v)).getCondicion());
                             producto12.setId(Lista_productos.get(re_products.getChildAdapterPosition(v)).getId());

                             producto12.setImagenUsuario(Lista_productos.get(re_products.getChildAdapterPosition(v)).getImagenUsuario());
                             producto12.setIdUsuario(Lista_productos.get(re_products.getChildAdapterPosition(v)).getIdUsuario());
                             producto12.setNombreUsuario(Lista_productos.get(re_products.getChildAdapterPosition(v)).getNombreUsuario());


                             o.putExtra("producto", producto12);

                             startActivity(o);
                         }
                     });


                     re_products.setAdapter(adaptador);
                 }else
                     {

                 Toast.makeText(getContext(),"No ha seleccionado una ubicación aún", Toast.LENGTH_LONG).show();
                     }



            }

        } catch (JSONException e) {
            e.printStackTrace();

            //Si no conecta se manda un Toast con el error
            Toast.makeText(getContext(),"No se pudo conectar "+e.toString(),Toast.LENGTH_SHORT).show();
            progress.hide();
        }

        flag = 0;
    }





}
