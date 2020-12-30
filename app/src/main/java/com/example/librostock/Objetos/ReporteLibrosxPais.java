package com.example.librostock.Objetos;

import java.util.ArrayList;

public class ReporteLibrosxPais {
    private String pais;
    private ArrayList<String> titulos;
    private String cantidad;

    public ReporteLibrosxPais(){ }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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
