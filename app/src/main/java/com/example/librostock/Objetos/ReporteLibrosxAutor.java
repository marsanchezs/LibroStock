package com.example.librostock.Objetos;

import java.util.ArrayList;

public class ReporteLibrosxAutor {
    private String autor;
    private ArrayList<String> titulos;
    private String cantidad;

    public ReporteLibrosxAutor(){ }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public ArrayList<String> getTitulos() {
        return titulos;
    }

    public void setTitulos(ArrayList<String> titulos) {
        this.titulos = titulos;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
