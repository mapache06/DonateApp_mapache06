package com.example.donateapp.fragments_menu;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
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

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class FUser extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {
    private static final int COD_SELECCIONA = 10;
    private static final int COD_TOMAR = 20;
    private TextView Name, Username;
    private Persona obj = new Persona();
    private CircularImageView Image;
    private ProgressDialog progreso;
    private FloatingActionButton photo;

    private static final String CARPETA_PRINCIPAL = "misImagenesApp/";//directorio principal
    private static final String CARPETA_IMAGEN = "imagenes";//carpeta donde se guardan las fotos
    private static final String DIRECTORIO_IMAGEN = CARPETA_PRINCIPAL + CARPETA_IMAGEN;//ruta carpeta de directorios
    private String path;//almacena la ruta de la imagen
    private File fileImagen;
    private Bitmap bitmap;



    public FUser() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments().getParcelable("x") != null) {
            obj = getArguments().getParcelable("x");
        }


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
        }


        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarDialog();
            }
        });


        return view;
    }

    private void cargarDialog() {
        //Toast.makeText(getContext(),"Hola",Toast.LENGTH_SHORT).show();
        final CharSequence[] opciones={"Tomar Foto","Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("Elige una opción");

        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (opciones[i].equals("Tomar Foto")) {
                    //llamado al metodo tomar foto
                    //Toast.makeText(getContext(), "Tomar Foto", Toast.LENGTH_SHORT).show();
                    abrirCamara();

                } else {
                    if (opciones[i].equals("Elegir de Galeria")) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent,"Seleccione"),COD_SELECCIONA);
                    } else {
                        dialogInterface.dismiss();
                    }
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case COD_SELECCIONA:
                Uri miPath = data.getData();
                Image.setImageURI(miPath);
                break;

            case COD_TOMAR:
                MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {
                                Log.i("Path",""+path);
                            }
                        });
                bitmap= BitmapFactory.decodeFile(path);
                Image.setImageBitmap(bitmap);
                break;
        }
    }

    private void abrirCamara(){
        File miFile=new File(Environment.getExternalStorageDirectory(),DIRECTORIO_IMAGEN);
        boolean isCreada = miFile.exists();

        if(isCreada==false){
            isCreada=miFile.mkdirs();
        }

        if(isCreada==true){
            Long consecutivo= System.currentTimeMillis()/1000;
            String nombre=consecutivo.toString()+".jpg";

            path=Environment.getExternalStorageDirectory()+File.separator+DIRECTORIO_IMAGEN
                    +File.separator+nombre;//indicamos la ruta de almacenamiento

            fileImagen=new File(path);

            Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(fileImagen));

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
            {
                String authorities=getContext().getPackageName()+".provider";
                Uri imageUri= FileProvider.getUriForFile(getContext(),authorities,fileImagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }else
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fileImagen));
            }




            startActivityForResult(intent,COD_TOMAR);

            ////
        }
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

        //    Toast.makeText(getContext(),"Mensaje: "+response,Toast.LENGTH_SHORT).show();

        Persona miUsuario=new Persona();

        JSONArray json=response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            miUsuario.setData(jsonObject.optString("Imagen"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (miUsuario.getImage()!=null){
            Image.setImageBitmap(miUsuario.getImage());
        }else{
            Image.setImageResource(R.drawable.sinimagen);
        }
    }
}
