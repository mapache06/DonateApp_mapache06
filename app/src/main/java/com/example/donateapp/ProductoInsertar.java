package com.example.donateapp;

public class ProductoInsertar {

    private int Id;
    private int Usuario;
    private int Categoria;
    private int Condicion;
    private int Situacion;
    private int Ubicacion ;
    private String Titulo;
    private String Descripcion;
    private String Horarios;
    private String foto;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUsuario() {
        return Usuario;
    }

    public void setUsuario(int usuario) {
        Usuario = usuario;
    }

    public int getCategoria() {
        return Categoria;
    }

    public void setCategoria(int categoria) {
        Categoria = categoria;
    }

    public int getCondicion() {
        return Condicion;
    }

    public void setCondicion(int condicion) {
        Condicion = condicion;
    }

    public int getSituacion() {
        return Situacion;
    }

    public void setSituacion(int situacion) {
        Situacion = situacion;
    }

    public int getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(int ubicacion) {
        Ubicacion = ubicacion;
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

    public String getHorarios() {
        return Horarios;
    }

    public void setHorarios(String horarios) {
        Horarios = horarios;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
