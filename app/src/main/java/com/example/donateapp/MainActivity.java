package com.example.donateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    EditText user, pass;
    Button login;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = (Button) findViewById(R.id.btnLogin);
        user = (EditText) findViewById(R.id.editEmail);
        pass = (EditText) findViewById(R.id.editPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ingresar("http://192.168.15.25:8080/donateapp/buscar_usuario.php?user="+(user.getText().toString())+"&"+
                        "password="+(pass.getText().toString()));
            }
        });
    }

    public void Ingresar(String URL){
        final Persona obj = new Persona();
        final Intent o = new Intent(this, MostrarDatos.class);


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {

                        jsonObject = response.getJSONObject(i);
                        obj.id = jsonObject.getInt("ID");
                        obj.name = jsonObject.getString("Nombre").toString();
                        obj.lastName = jsonObject.getString("Apellido".toString());
                        obj.userName = jsonObject.getString("UserName").toString();
                        obj.eMail = jsonObject.getString("Correo").toString();
                        obj.password = jsonObject.getString("Contrasena").toString();

                        if (obj.name != null){
                            Bundle extras = getIntent().getExtras();

                            o.putExtra("persona",obj);
                            startActivity(o);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);



    }


    public void registrar(View v){
        Intent i = new Intent(this, RegisterNombre.class);

        startActivity(i);


    }



}
