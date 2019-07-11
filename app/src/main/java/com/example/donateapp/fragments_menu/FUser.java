package com.example.donateapp.fragments_menu;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Color;
import android.hardware.camera2.CameraDevice;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.ByteArrayPool;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.donateapp.Main2Activity;
import com.example.donateapp.MainActivity;
import com.example.donateapp.Persona;
import com.example.donateapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.example.donateapp.R;
import com.example.donateapp.Persona;
import com.example.donateapp.VolleySingleton;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 */

//En este fragment se mostra informacion basica del usuario que ingresa
public class FUser extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {

    private TextView Name, Username;
    private Persona obj = new Persona();
    private CircularImageView Image;
    private ProgressDialog progreso;
    private FloatingActionButton photo;
    private Button botonCargar;


   public FUser() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Se valida si se mando del activity anterior un objeto de ser así, lo recibe
        if (getArguments().getParcelable("x") != null) {
            obj = getArguments().getParcelable("x");
        }



        //se validan los permisos para escribir en la memoria
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }


    }


    String currentPhotoPath;

   //con el siguiente metodo se crea un directorio para la imagen y un nombre
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Backup_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    static final int REQUEST_TAKE_PHOTO = 1;

    //con este metodo se realiza la peticion para abrir la camara
    private void tomarFoto(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.toString());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }



static final int REQUEST_IMAGE_CAPTURE = 1;


    //Aqui se hace un set al ImageView y se manda a llamar al metodo ejecutar servicio que guarga la foto en el servidor
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Image.setImageBitmap(imageBitmap);

            String ip1 = getString(R.string.ip);
            final String url = "http://"+ip1+"/donateapp/subir_fotoPerfil.php";

            ejecutarServicio(url, imageBitmap);
        }
    }



//Con la libreria picasso se cargan las fotos de un url
    private void loadImagenByInternetbyPicasso() {
        String ip = getString(R.string.ip);
        String urlImagen = "http://"+ip+"/donateapp/";
        String urlfinal = urlImagen+(obj.RutaImagen).toString();

        Picasso.get()
                .load(urlfinal)
                .into(Image);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fuser, container, false);

        Name = (TextView) view.findViewById(R.id.fu_Nombre);
        Username = (TextView) view.findViewById(R.id.fu_UserName);
        Image = (CircularImageView) view.findViewById(R.id.foto_perfil);
        photo = (FloatingActionButton) view.findViewById(R.id.Image_photo);
        String xx = getString(R.string.Nombre);


        int color = Color.parseColor("#EC676B");
        photo.setColorFilter(color);


        if (obj.name != null && Name.getText().toString() == xx) {
            Name.setText(obj.name.toString());
            Username.setText(obj.userName.toString());
            cargarWebService();
            loadImagenByInternetbyPicasso();
        }


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto(v);
            }
        });

        return view;
    }

    private void cargarWebService() {

        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();

        String ip=getString(R.string.ip);

        String url="http://"+ip+"/donateapp/wsJSONConsultarUsuarioImagen.php?Correo="
                +obj.eMail.toString();

        JsonObjectRequest jsonObjectRequest =new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        // request.add(jsonObjectRequest);
        VolleySingleton.getIntanciaVolley(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),"No se pudo Consultar "+error.toString(),Toast.LENGTH_SHORT).show();
        Log.i("ERROR",error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {
        progreso.hide();

         //   Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();

        Persona miUsuario=new Persona();

        JSONArray json=response.optJSONArray("usuario");
        JSONObject jsonObject=null;


    }




//Aqui se guarda la foto en el servidor
    private void ejecutarServicio(String URL, final Bitmap im){
        progreso=new ProgressDialog(getContext());
        progreso.setMessage("Consultando...");
        progreso.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progreso.hide();


                if (response.equalsIgnoreCase("error")){
                    Toast.makeText(getContext(), "No se ha podido actualizar la foto", Toast.LENGTH_SHORT).show();
                }else{
                    obj.RutaImagen = "imagenes/"+ obj.id + ".jpg";
                    Toast.makeText(getContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Id", Integer.toString(obj.id));
                parametros.put("Imagen",convertirBitmapString(im));

                return parametros;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
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
