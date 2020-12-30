package com.example.librostock.Objetos;

import java.util.ArrayList;

public class ReporteLibrosxGenero {
    private String genero;
    private ArrayList<String> titulos;
    private String cantidad;

    public ReporteLibrosxGenero(){ }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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
