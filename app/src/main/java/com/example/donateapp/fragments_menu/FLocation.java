package com.example.donateapp.fragments_menu;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.example.donateapp.UbicacionInfo;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
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
    Marker marcador;
    private static final int LOCATION_REQUEST_CODE = 1;
    SearchView sv;
    EditText et;
    ImageView icon, icon2;

    public FLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_flocation, container, false);

        sv = (SearchView) v.findViewById(R.id.sv_location);
        et = (EditText) sv.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        et.setTextColor(getResources().getColor(R.color.Negro));
        et.setHintTextColor(getResources().getColor(R.color.Textos));
        icon = (ImageView) sv.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        icon.setColorFilter(getResources().getColor(R.color.Textos));
        icon2 = (ImageView) sv.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
        icon2.setColorFilter(getResources().getColor(R.color.Textos));

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = sv.getQuery().toString();
                List<Address> addressList = null;

                if(location != null || !location.equals("")){
                    Geocoder geocoder = new Geocoder(getContext());
                    try{

                        addressList = geocoder.getFromLocationName(location,1);

                    }catch (Exception e){

                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    if(marcador != null){
                        marcador.remove();
                        marcador = mapa.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title("Mi ubicación")
                                .snippet("Este será el punto de referencia")
                        );
                    }

                    mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,17));

                    lat = latLng.latitude;
                    lon = latLng.longitude;

                    insertarUbicacion();
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //Se valida si se mando del activity anterior un objeto de ser así, lo recibe
        if (getArguments().getParcelable("x") != null) {
            obj = getArguments().getParcelable("x");
            lat = obj.Latitud;
            lon = obj.Altitud;
        }

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);


        if (mapFragment == null) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();


        }
        if(lat == 0) {
            UbicacionInfo.newInstance("Mapas",
                    getString(R.string.InfoUbicacion1))
                    .show(getFragmentManager(), null);
        }

        mapFragment.getMapAsync(this);



        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        rlp.setMargins(0, 0, 30, 30);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapa = googleMap;
        mapa.getUiSettings().setZoomControlsEnabled(true);

        LatLng ubicacionActual;

        if(lat != 0){
            ubicacionActual = new LatLng(lat,lon);
            marcador = mapa.addMarker(new MarkerOptions()
                    .position(ubicacionActual)
                    .title("Mi ubicación")
                    .snippet("Este será el punto de referencia")
                    .draggable(true)
            );
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionActual, 15));
        }else{
            LatLng Monterrey = new LatLng(25.6397836, -100.2931016);
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(Monterrey, 15));
        }






        // Controles UI
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mapa.setMyLocationEnabled(true);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Mostrar diálogo explicativo
            } else {
                // Solicitar permiso
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }

        mapa.getUiSettings().setZoomControlsEnabled(false);



        mapa.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                if(marcador != null){
                    marcador.remove();
                }


                marcador = mapa.addMarker(new MarkerOptions()
                .position(latLng)
                        .title("Mi ubicación")
                        .snippet("Este será el punto de referencia")
                        .draggable(true)

                );



                LatLng position = marcador.getPosition();
                lat = position.latitude;
                lon = position.longitude;

                insertarUbicacion();

            }





        });

        mapa.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                if(marker != null){
                    marker.remove();
                }


                marcador = mapa.addMarker(new MarkerOptions()
                        .position(marker.getPosition())
                        .title("Mi ubicación")
                        .snippet("Este será el punto de referencia")
                        .draggable(true)

                );



                LatLng position = marker.getPosition();
                lat = position.latitude;
                lon = position.longitude;

                insertarUbicacion();


            }
        });



        mapa.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if (marker.equals(marcador)){
                    UbicacionInfo.newInstance(marker.getTitle(),
                            getString(R.string.InfoUbicacion))
                            .show(getFragmentManager(), null);
                }
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
