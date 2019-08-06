package com.example.donateapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


//Aqui se puede iniciar sesion
public class MainActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    private EditText user, pass;
    private Button login;
    private RequestQueue requestQueue;
    private ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        login = (Button) findViewById(R.id.btnLogin);
        user = (EditText) findViewById(R.id.editEmail);
        pass = (EditText) findViewById(R.id.editPassword);

        //Si se selecciona el boton ingresar se manda a llamar el metos ConsultarUsuario()
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ingresar("http://192.168.0.8:8080/donateapp/buscar_usuario.php?user="+(user.getText().toString())+"&"+
                  //      "password="+(pass.getText().toString()));
            ConsultarUsuario();
            }
        });
    }

    //Si se selecciona registrar usuario, te manda a otro activity
    public void registrar(View v){
        Intent i = new Intent(this, RegisterNombre.class);

        startActivity(i);


    }


//se hace una consulta a la base de datos para verificar si existe el usuario
    public void ConsultarUsuario(){
        progreso=new ProgressDialog(this);
        progreso.setMessage("Consultando...");
        progreso.show();

        String ip=getString(R.string.ip);

        String url="http://"+ip+"/donateapp/Validar_Usuario.php?Correo="
                +(user.getText().toString()+"&"+
                      "Pass="+(pass.getText().toString()));

            JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,url,null,  this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(jsonObjectRequest);
    }


    //Si se produce un error se escribe en un toast
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(this,"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    //Se asigno los valores del usuario en un objeto Persona
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
}
