package com.example.donateapp;
//Aqui se almacena la informacion de la tabla Producto de la base de datos y se hace un parcelable a la clase
public class Productos {
    private String Titulo, Descripcion;
    private int fotoProducto;

    public Productos(){

    }

    public Productos(String titulo, String descripcion, int fotoProducto) {
        Titulo = titulo;
        Descripcion = descripcion;
        this.fotoProducto = fotoProducto;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getFotoProducto() {
        return fotoProducto;
    }

    public void setFotoProducto(int fotoProducto) {
        this.fotoProducto = fotoProducto;
    }
}
