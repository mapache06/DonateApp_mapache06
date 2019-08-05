package com.example.donateapp.fragments_menu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.donateapp.Persona;
import com.example.donateapp.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

//En este fragment se podrá asignar la ubicacion donde se desea entregar el producto a donar
public class FLocation extends Fragment
implements OnMapReadyCallback {

SupportMapFragment mapFragment = new SupportMapFragment();
GoogleMap mapa;
Double lat, lon;
Persona obj = new Persona();
    public FLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_flocation, container, false);

        //Se valida si se mando del activity anterior un objeto de ser así, lo recibe
        if (getArguments().getParcelable("x") != null) {
            obj = getArguments().getParcelable("x");
        }

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);


        if(mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();


        }

        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapa = googleMap;
        mapa.getUiSettings().setZoomControlsEnabled(true);

        LatLng Monterrey = new LatLng(25.6397836,-100.2931016);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Monterrey,15));

        mapa.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {


               Marker marker = mapa.addMarker(new MarkerOptions()
                .position(latLng)
                );

                LatLng position = marker.getPosition();
                lat = position.latitude;
                lon = position.longitude;

                insertarUbicacion();
            }





        });

    }

    private void insertarUbicacion() {
        String ip1 = getString(R.string.ip);
        final String url = "http://"+ip1+"/donateapp/";
        String urlInsertar;
        final int idvar;

        if(obj.ubicacion == 0){
            urlInsertar = url+"InsertarUbicacion.php";
            obj.ubicacion = 1;
            idvar = obj.id;
        }else{
            urlInsertar = url+"ActualizarUbicacion.php";
            idvar=obj.ubicacion;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Latitud",String.valueOf(lat));
                parametros.put("Altitud", String.valueOf(lon));
                parametros.put("Id", Integer.toString(idvar));

                return parametros;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


    }

}
