package com.example.donateapp;

import android.os.Parcel;
import android.os.Parcelable;

//Aqui se almacena la informacion de la tabla Producto de la base de datos y se hace un parcelable a la clase
public class Productos implements Parcelable {
    private int Id;
    private String Titulo, Descripcion;
    private String fotoProducto;

    private String HorariosDeRecoleccion;
    private String Categoría;
    private String condicion;
    private String situacion;
    private String Altitud;
    private String Latitud;
    private int IdUsuario;
    private String NombreUsuario;
    private String ImagenUsuario;



    public Productos(){}

    protected Productos(Parcel in) {
        Id = in.readInt();
        Titulo = in.readString();
        Descripcion = in.readString();
        fotoProducto = in.readString();
        HorariosDeRecoleccion = in.readString();
        Categoría = in.readString();
        condicion = in.readString();
        situacion = in.readString();
        Altitud = in.readString();
        Latitud = in.readString();
        NombreUsuario = in.readString();
        ImagenUsuario = in.readString();
        IdUsuario = in.readInt();
    }


    public static final Creator<Productos> CREATOR = new Creator<Productos>() {
        @Override
        public Productos createFromParcel(Parcel in) {
            return new Productos(in);
        }

        @Override
        public Productos[] newArray(int size) {
            return new Productos[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(Id);
        dest.writeString(Titulo);
        dest.writeString(Descripcion);
        dest.writeString(fotoProducto);
        dest.writeString(HorariosDeRecoleccion);
        dest.writeString(Categoría);
        dest.writeString(condicion);
        dest.writeString(situacion);
        dest.writeString(Altitud);
        dest.writeString(Latitud);
        dest.writeString(NombreUsuario);
        dest.writeString(ImagenUsuario);
        dest.writeInt(IdUsuario);
    }

//
    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public int getIdUsuario() { return IdUsuario; }

    public void setNombreUsuario(String NombreUsuario) {
        this.NombreUsuario = NombreUsuario;
    }

    public String getNombreUsuario() {
        return NombreUsuario;
    }


    public void setImagenUsuario(String ImagenUsuario) {
        this.ImagenUsuario = ImagenUsuario;
    }

    public String getImagenUsuario() {
        return ImagenUsuario;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getId() { return Id; }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getTitulo() {
        return Titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getFotoProducto() {
        return fotoProducto;
    }

    public void setFotoProducto(String fotoProducto) {
        this.fotoProducto = fotoProducto;
    }

    public String getHorariosDeRecoleccion() { return HorariosDeRecoleccion; }

    public void setHorariosDeRecoleccion(String horariosDeRecoleccion) {
        HorariosDeRecoleccion = horariosDeRecoleccion;
    }

    public String getCategoría() {
        return Categoría;
    }

    public void setCategoría(String categoría) {
        Categoría = categoría;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getAltitud() {
        return Altitud;
    }

    public void setAltitud(String altitud) {
        Altitud = altitud;
    }

    public String getLatitud() {
        return Latitud;
    }

    public void setLatitud(String latitud) {
        Latitud = latitud;
    }
}
