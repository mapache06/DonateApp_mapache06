package com.example.donateapp;

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
