package com.example.donateapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterPassword extends AppCompatActivity {
    EditText pass, confirmpass;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_contrasena);

        pass = (EditText) findViewById(R.id.editContrasena);
        confirmpass = (EditText) findViewById(R.id.editConfirContraseña);
        next = (Button) findViewById(R.id.btnNextMostr);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ejecutarServicio("http://192.168.15.25:8080/donateapp/insertar_usuario.php");

            }
        });
    }

    private void ejecutarServicio(String URL){

        Bundle extras = getIntent().getExtras();
        final Persona obj = extras.getParcelable("persona");
        obj.password = pass.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RegisterPassword.this, MainPage.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("name",obj.name.toString());
                parametros.put("lastName", obj.lastName.toString());
                parametros.put("userName", obj.userName.toString());
                parametros.put("email", obj.eMail.toString());
                parametros.put("pass", obj.password.toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}