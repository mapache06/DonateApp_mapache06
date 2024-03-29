package com.example.donateapp;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import org.json.JSONException;


//Aqui se almacena la informacion de la tabla Usuario de la base de datos y se hace un parcelable a la clase
public class Persona implements Parcelable {

    public int id;
    public String name;
    public String lastName;
    public String eMail;
    public String password;
    public String userName;
    public String data;
    public String RutaImagen;
    public Bitmap image;
    public int ubicacion;
    public double Altitud;
    public double Latitud;

    public Persona(){};

    protected Persona(Parcel in) {
        id = in.readInt();
        name = in.readString();
        lastName = in.readString();
        eMail = in.readString();
        password = in.readString();
        userName = in.readString();
        RutaImagen = in.readString();
        data = in.readString();
        ubicacion = in.readInt();
        Altitud = in.readDouble();
        Latitud = in.readDouble();
        //image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Persona> CREATOR = new Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(lastName);
        dest.writeString(eMail);
        dest.writeString(password);
        dest.writeString(userName);
        dest.writeString(RutaImagen);
        dest.writeString(data);
        dest.writeInt(ubicacion);
        dest.writeDouble(Altitud);
        dest.writeDouble(Latitud);
       // dest.writeParcelable(image,0);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        try {
            byte[] byteCode = Base64.decode(data, Base64.DEFAULT);
            this.image = BitmapFactory.decodeByteArray(byteCode, 0, byteCode.length);
        }catch(Exception e){
            e.printStackTrace();
        }
        }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
