package com.example.donateapp;

import java.util.Calendar;
import java.util.Date;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InsertarProducto extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {

    ProductoInsertar producto = new ProductoInsertar();
    Productos producto1 = new Productos();
    Persona obj = new Persona();

    private Spinner CategoriaSpinner;
    private Spinner CondicionSpinner;
    private Spinner AmSpinner;
    private Spinner PmSpinner;

    private Bitmap bitmap12;
    private ProgressDialog progreso;
    private ImageView ImagenProductoInsertar;
    private Button Publicar;
    private EditText Titulo,Descripcion;

    Date fecha = new Date();

    JsonObjectRequest jsonObject;

    ArrayList<String >categoriaItems = new ArrayList<>();
    ArrayList<String >condicionItems = new ArrayList<>();

    private String hora1, hora2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertar_producto);

        //se valida si se mandaron elementos y los recibe
        Bundle extras = new Bundle();
        extras = getIntent().getExtras();

        if(extras.getParcelable("persona")!= null) {
            obj = extras.getParcelable("persona");

        }



        CategoriaSpinner = (Spinner) findViewById(R.id.CategoriaSpinner);
        CondicionSpinner = (Spinner) findViewById(R.id.CondicionSpinner);
        AmSpinner = (Spinner) findViewById(R.id.AmSpinner);
        PmSpinner = (Spinner) findViewById(R.id.PmSpinner);

        ImagenProductoInsertar = (ImageView) findViewById(R.id.ImagenProducto);
        Publicar = (Button) findViewById(R.id.botonPublicar);
        Titulo = (EditText) findViewById(R.id.TituloProductoInsertar);
        Descripcion = (EditText) findViewById(R.id.DescripcionProductoInsertar);





        categoriaItems.add("Seleccione");
        condicionItems.add("Seleccione");
        ConsultarCategorias();
        ConsultarCondicion();
        loadImagenByInternetbyPicasso();



        if(extras.getParcelable("producto")!= null) {
            producto1 = extras.getParcelable("producto");

            Titulo.setText(producto1.getTitulo().toString());
            Descripcion.setText(producto1.getDescripcion().toString());

            String ip = "192.168.34.41:8080";
            String urlImagen = "http://"+ip+"/donateapp/productoImagen/";

            Picasso.get()
                    .load(urlImagen+(producto1.getFotoProducto()).toString())
                    .resize(800,800)
                    .centerInside()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ImagenProductoInsertar);
                        producto.setId(producto1.getId());
        }

        ArrayAdapter<CharSequence> adapterCondicion = new ArrayAdapter(this, R.layout.spinner_item, condicionItems);

        ArrayAdapter<CharSequence> adapterAm = ArrayAdapter.createFromResource(this,
                R.array.comboHorariosPm, R.layout.spinner_item);

        ArrayAdapter<CharSequence> adapterPm = ArrayAdapter.createFromResource(this,
                R.array.comboHorariosPm, R.layout.spinner_item);

        ArrayAdapter<CharSequence> adapterCategoria = new ArrayAdapter(this, R.layout.spinner_item, categoriaItems);





        //con las 2 lineas de codigo siguiente se le cambia el color al boton flotante
        //int color = Color.parseColor("#E61E24");
        //ImagenProductoInsertar.setColorFilter(color);


        CondicionSpinner.setAdapter(adapterCondicion);
        AmSpinner.setAdapter(adapterAm);
        PmSpinner.setAdapter(adapterPm);
        CategoriaSpinner.setAdapter(adapterCategoria);

        CategoriaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    producto.setCategoria(position);
                } else {
                     Toast.makeText(parent.getContext(),"Tiene que seleccionar una categoría"
                     , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CondicionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    producto.setCondicion(position);
                } else {
                    Toast.makeText(parent.getContext(),"Tiene que seleccinar la condicion del producto ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hora1 = parent.getItemAtPosition(position)
                        .toString();

                Toast.makeText(parent.getContext(),"Seleccionado: "+ parent.getItemAtPosition(position)
                        .toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        PmSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(parent.getContext(),"Seleccionado: "+ parent.getItemAtPosition(position)
                        .toString(), Toast.LENGTH_LONG).show();

                hora2 = parent.getItemAtPosition(position)
                        .toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        Publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            producto.setTitulo(Titulo.getText().toString());
            producto.setDescripcion(Descripcion.getText().toString());
            producto.setHorarios("De "+hora1 + " a " + hora2);
            producto.setSituacion(2);
            producto.setUsuario(producto1.getIdUsuario());

                String ip1 = getString(R.string.ip);
            final String url =  "http://"+ip1+"/donateapp/SubirProducto.php";
            final String url2 = "http://"+ip1+"/donateapp/actualizarProducto.php";

            if(producto1.getId() != 0){
                ImagenProductoInsertar.buildDrawingCache();
                bitmap12 = ImagenProductoInsertar.getDrawingCache();
                producto.setId(producto1.getId());
                ejecutarServicio(url2, bitmap12);

            }else{
                producto.setUsuario(obj.id);
                ejecutarServicio(url, bitmap12);
            }

            }
        });






    }


private void cargarImagen(){
        final CharSequence[] opciones = {"Tomar Foto", "Cargar Imagen", "Cancelar"};
        final AlertDialog.Builder alertaOpciones = new AlertDialog.Builder(InsertarProducto.this);
        alertaOpciones.setTitle("Seleccione una Opción");

        alertaOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("Tomar Foto") ){
                tomarFoto();
                }else{
                    if(opciones[which].equals("Cargar Imagen")){
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.setType("image/");
                        startActivityForResult(i.createChooser(i,"Seleccione la aplicacion"), 10);
                    }else{
                        dialog.dismiss();
                    }
                }
            }
        });

        alertaOpciones.show();

}

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode != REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String ip1 = getString(R.string.ip);
            final String url = "http://" + ip1 + "/donateapp/subir_fotoPerfil.php";

            Uri path = data.getData();
            ImagenProductoInsertar.setImageURI(path);


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), path);
                bitmap12 = bitmap;
                //ejecutarServicio(url, bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImagenProductoInsertar.setImageBitmap(imageBitmap);




            bitmap12 = imageBitmap;
            //ejecutarServicio(url, imageBitmap);
        }


    }
    static final int REQUEST_TAKE_PHOTO = 1;

    private void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.toString());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }
    private void loadImagenByInternetbyPicasso() {
        String ip = getString(R.string.ip);
        String urlImagen = "http://"+ip+"/donateapp/";
        String urlfinal = urlImagen+"insert_picture.png".toString();

        Picasso.get()
                .load(urlfinal)
                .resize(800,400)
                .centerInside()
                .placeholder(R.mipmap.ic_launcher)
                .into(ImagenProductoInsertar);

    }


    public void ConsultarCategorias(){

        String ip=getString(R.string.ip);

        String url="http://"+ip+"/donateapp/LlenarSpinnerCategoria.php";

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,url,null,  this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(jsonObjectRequest);
    }


    //Si se produce un error se escribe en un toast
    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(this,"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    //Se asigno los valores del usuario en un objeto Persona
    @Override
    public void onResponse(JSONObject response) {

        if(response.optJSONArray("categoria") != null) {
            JSONArray json = response.optJSONArray("categoria");


            try {

                for (int i = 0; i < json.length(); i++) {
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);

                    categoriaItems.add(jsonObject.optString("Descripcion"));
                }

            } catch (JSONException e) {
                System.out.println("Error XDDDDD: " + e);
            }

        }else if(response.optJSONArray("condicion") != null){
            JSONArray json = response.optJSONArray("condicion");


            try {

                for (int i = 0; i < json.length(); i++) {
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);

                    condicionItems.add(jsonObject.optString("Descripcion"));
                }

            } catch (JSONException e) {
                System.out.println("Error XDDDDD: " + e);
            }
        }
    }



    public void ConsultarCondicion() {

        String ip = getString(R.string.ip);

        String url = "http://" + ip + "/donateapp/LlenarSpinnerCondicion.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(this).addToRequestQueue(jsonObjectRequest);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void onClick(View view) {
        cargarImagen();
    }

    String currentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Backup_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void ejecutarServicio(String URL, final Bitmap im){
        progreso=new ProgressDialog(this);
        progreso.setMessage("Publicando Producto...");
        progreso.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();


                if (response.equalsIgnoreCase("error")){
                    Toast.makeText(getApplication(), "No se ha podido publicar el artículo", Toast.LENGTH_SHORT).show();
                }else{
                    obj.RutaImagen = obj.id + ".jpg";
                    Toast.makeText(getApplication(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();

                String dia = Integer.toString(Calendar.DATE);
                String mes = Integer.toString(Calendar.MONTH);
                String ano = Integer.toString(Calendar.YEAR);
                String hora = Integer.toString(Calendar.HOUR);
                String minuto = Integer.toString(Calendar.MINUTE);
                String segundo = Integer.toString(Calendar.SECOND);
                String fecha ="_"+dia+"_"+mes+"_"+ano+"_"+hora+"_"+minuto+"_"+segundo;


                parametros.put("foto", producto.getUsuario()+fecha+ "_"+producto.getTitulo().toString().trim() );
                parametros.put("Imagen",convertirBitmapString(im));

                parametros.put("Usuario",Integer.toString(producto.getUsuario()).toString());
                parametros.put("Categoria",Integer.toString(producto.getCategoria()).toString());

                parametros.put("Condicion",Integer.toString(producto.getCondicion()).toString());
                parametros.put("Situacion",Integer.toString(producto.getSituacion()).toString());

                parametros.put("Ubicacion","1");
                parametros.put("Id",Integer.toString(producto.getId()).toString());

                parametros.put("Titulo",producto.getTitulo().toString());
                parametros.put("Descripcion",producto.getDescripcion().toString());

                parametros.put("Horarios",producto.getHorarios().toString());


                return parametros;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private String convertirBitmapString(Bitmap bitmap){
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,array);
        byte[] imagenByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imagenByte,Base64.DEFAULT);


        return imagenString;
    }
}
