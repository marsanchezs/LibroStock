package com.example.librostock.Informes;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;

public class LetrasReportes {

    public static Font letraTitulo = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
    public static Font letraSubTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    public static Font letraCabeceraTabla = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.WHITE);
    public static Font letraDatosTabla = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
    public static Font letraNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    public static Font letraResaltada = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);
    public static Font letraTituloReporte = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, BaseColor.WHITE);
    public static Font letraReporte = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.WHITE);

    public LetrasReportes(){ }

}
