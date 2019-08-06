package com.example.donateapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.donateapp.fragments_menu.FHome;
import com.example.donateapp.fragments_menu.FList;
import com.example.donateapp.fragments_menu.FLocation;
import com.example.donateapp.fragments_menu.FMessage;
import com.example.donateapp.fragments_menu.FUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//En este activity se crea la barra de navegacion inferior que la app tiene
public class Main2Activity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {
    //private TextView mTextMessage;
    private BottomNavigationView bottom_nav;
    private Persona obj = new Persona();
    private Bundle bundle = new Bundle();
    ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //se valida si se mandaron elementos y los recibe
        Bundle extras = getIntent().getExtras();
        if(extras.getParcelable("persona")!= null) {
            obj = extras.getParcelable("persona");

        }



        Fragment f = new FHome();

        bottom_nav = findViewById(R.id.nav_view);

        //Se pone por defecto el fragment home
        cambiarFragment(f);
        bottom_nav.setSelectedItemId(R.id.nav_Home);



        //Aqui se valida que fragment se desea acceder
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment  f = new Fragment();
                switch(menuItem.getItemId())
                {
                    case R.id.nav_Home:
                        consultarUsuario();
                        f = new FHome();


                        f.setArguments(bundle);

                        bundle.putParcelable("x",obj);
                        cambiarFragment(f);

                        return true;

                    case R.id.nav_Location:
                        consultarUsuario();
                        f = new FLocation();
                        f.setArguments(bundle);

                        bundle.putParcelable("x",obj);
                        cambiarFragment(f);

                        return true;

                    case R.id.nav_Message:
                        f = new FMessage();
                        f.setArguments(bundle);
                        bundle.putParcelable("x",obj);
                        cambiarFragment(f);

                        return true;

                    case R.id.nav_More:
                        f = new FList();
                        f.setArguments(bundle);
                        bundle.putParcelable("x",obj);
                        cambiarFragment(f);


                        return true;

                    case R.id.nav_User:
                        f = new FUser();
                        f.setArguments(bundle);
                        bundle.putParcelable("x",obj);

                        cambiarFragment(f);


                        return true;
                }

                return false;
            }
        });
    }

    private void consultarUsuario() {

        progreso = new ProgressDialog(this);
        progreso.setMessage("Buscando informació...");
        progreso.show();

        String ip=getString(R.string.ip);

        String url="http://"+ip+"/donateapp/Validar_Usuario.php?Correo="
                +obj.eMail.toString()+"&"+
                "Pass="+obj.password.toString();

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,url,null,  this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(jsonObjectRequest);
    }


    //con este metodo se cambia el fragment
    private void cambiarFragment(Fragment fragment){
        fragment.setArguments(bundle);
        bundle.putParcelable("x",obj);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(this,"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        //    Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();

        JSONArray json=response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            obj.id = jsonObject.optInt("Id");
            obj.name = jsonObject.optString("Nombre").toString();
            obj.lastName = jsonObject.optString("Apellido".toString());
            obj.userName = jsonObject.optString("UserName").toString();
            obj.eMail = jsonObject.optString("Correo").toString();
            obj.password = jsonObject.optString("Contrasena").toString();
            obj.data=jsonObject.optString("Imagen");
            obj.RutaImagen = jsonObject.optString("RutaImagen");
            obj.ubicacion = jsonObject.optInt("Ubicacion");
            obj.Latitud = jsonObject.optDouble("Latitud");
            obj.Altitud = jsonObject.optDouble("Altitud");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

