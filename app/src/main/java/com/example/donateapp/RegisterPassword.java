package com.example.donateapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterPassword extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    private EditText pass, confirmpass;
    private Button next;
    ProgressDialog progreso;
    Persona obj;

    //Aqui solo se le solicita la contraseña a usuario a registrae y se guuarda en una propiedad del usuario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_contrasena);

        Bundle extras = getIntent().getExtras();

        //Se valida si se mando del activity anterior un objeto de ser así, lo recibe
        if (extras.getParcelable("persona") != null) {
            obj = extras.getParcelable("persona");
        }

        pass = (EditText) findViewById(R.id.editContrasena);
        confirmpass = (EditText) findViewById(R.id.editConfirContrasena);
        next = (Button) findViewById(R.id.btnNextMostr);

        String ip1 = getString(R.string.ip);
        final String url = "http://"+ip1+"/donateapp/insertar_usuario.php";

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String i = pass.getText().toString();
                String p = confirmpass.getText().toString();

                    if(i.equals("") || p.equals(""))
                    {
                        Toast.makeText(getBaseContext(), "No puede haber campos vacíos",Toast.LENGTH_SHORT).show();
                    }else if(i.equals(p)){
                        obj.password = i;
                        ejecutarServicio(url);
                    } else {
                        Toast.makeText(getBaseContext(), "Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();

                    }

            }
        });
    }

    //con este metodo se insertan los datos del usuario a registrar en la base de datos
    private void ejecutarServicio(String URL){

        // obj.password = pass.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();

                ConsultarUsuario();


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
    public void ConsultarUsuario(){
        progreso=new ProgressDialog(this);
        progreso.setMessage("Consultando...");
        progreso.show();

        String ip=getString(R.string.ip);

        String url="http://"+ip+"/donateapp/Validar_Usuario.php?Correo="
                +(obj.userName.toString()+"&"+
                "Pass="+(obj.password.toString()));

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,url,null,  this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();
        final Intent o = new Intent(this, Main2Activity.class);

        //    Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();

        Persona miUsuario=new Persona();

        JSONArray json=response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            miUsuario.id = jsonObject.optInt("Id");
            miUsuario.name = jsonObject.optString("Nombre").toString();
            miUsuario.lastName = jsonObject.optString("Apellido".toString());
            miUsuario.userName = jsonObject.optString("UserName").toString();
            miUsuario.eMail = jsonObject.optString("Correo").toString();
            miUsuario.password = jsonObject.optString("Contrasena").toString();
            miUsuario.data=jsonObject.optString("Imagen");
            miUsuario.RutaImagen = jsonObject.optString("RutaImagen");
            miUsuario.ubicacion = jsonObject.optInt("Ubicacion");
            miUsuario.Latitud = jsonObject.optDouble("Latitud");
            miUsuario.Altitud = jsonObject.optDouble("Altitud");
            if (miUsuario.name != null) {
                if (miUsuario.id == 0) {
                    Toast.makeText(this, "Usuario ó Contraseña Incorrectos", Toast.LENGTH_SHORT).show();
                } else {
                    Bundle extras = getIntent().getExtras();

                    o.putExtra("persona", miUsuario);
                    startActivity(o);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(this,"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }
}