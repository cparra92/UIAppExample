package com.example.telematica.uiappexample.models;

import io.realm.RealmObject;

/**
 * Created by franciscocabezas on 12/11/15.
 */
public class Libro extends RealmObject {

    private int id;
    private String nombre;
    private String editorial;
    private String genero;
    private int autor;

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }
}
