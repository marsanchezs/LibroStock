package com.example.librostock.Informes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.Objetos.ReporteLibrosxGenero;
import com.example.librostock.Objetos.ReporteLibrosxPais;
import com.example.librostock.Utilidades.Utilidades;
import com.example.librostock.VisorPDF;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class ReportePais {
    private Context contexto;
    private File archivoPDF;
    private Document documento;
    private PdfWriter pdfEscritor;
    private Paragraph parrafo;
    private Utilidades utlilidades = new Utilidades();
    private LibroStockDelegar delegar = new LibroStockDelegar();

    public ReportePais(Context contexto) {
        this.contexto = contexto;
    }

    private void cargarDatos(){
        String fecha = utlilidades.traerFecha();
        String hora = utlilidades.traerHora();
        String[] encabezadoReporteAutor = {"PAÍS", "TÍTULOS", "CANTIDAD"};
        agregarMetadatos("Reporte País", "Número Libros x País", "LibroStock");
        agregarTitulos("Reporte Libros x País", "Número Libros x País", fecha, hora);
        //agregarParrafo(textoCorto);
        //agregarParrafo(textLargo);
        ArrayList<ReporteLibrosxPais> listaReporteLibrosxPais = delegar.reporteLibrosxPais(contexto);
        System.out.println("LISTA REPORTE LIBROS x PAÍS: "+listaReporteLibrosxPais.toString());
        crearTabla(encabezadoReporteAutor, listaReporteLibrosxPais);
        //crearTabla2(encabezadoReporteAutor, listaReporteLibrosxAutor);
    }

    public void abrirDocumentoPDF(){
        crearArchivo();
        try{
            documento = new Document(PageSize.LETTER);
            pdfEscritor = PdfWriter.getInstance(documento, new FileOutputStream(archivoPDF));
            documento.open();
            cargarDatos();

        }catch(Exception e){
            Log.e("abrirDocumentoPDF", e.toString());
        }
    }

    private void crearArchivo(){
        File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File carpeta = new File(ruta.getAbsolutePath(), "PDF");
        if(!carpeta.exists())
            carpeta.mkdirs();
        archivoPDF = new File(carpeta, "ReportePais.pdf");
    }

    public void cerrarDocumento(){
        documento.close();
    }

    public void agregarMetadatos(String titulo, String tema, String autor){
        documento.addTitle(titulo);
        documento.addSubject(tema);
        documento.addAuthor(autor);
    }

    public void agregarTitulos(String titulo, String subTitulo, String fecha, String hora){
        try{
            parrafo = new Paragraph();
            parrafo.setIndentationLeft(10);
            PdfPTable tablaEncabezado = new PdfPTable(3);
            tablaEncabezado.setWidthPercentage(100);
            tablaEncabezado.setWidths(new int[]{30, 80, 40});
            PdfPCell celda;
            Image imagen = utlilidades.traerLogo(contexto);
            celda = new PdfPCell(imagen, true);
            //celda.setPadding(10);
            celda.setRowspan(2);
            celda.setFixedHeight(70);
            tablaEncabezado.addCell(celda);
            celda = new PdfPCell(new Phrase(titulo, LetrasReportes.letraTituloReporte));
            celda.setBackgroundColor(BaseColor.DARK_GRAY);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setPaddingLeft(10f);
            tablaEncabezado.addCell(celda);
            celda = new PdfPCell(new Phrase(fecha, LetrasReportes.letraReporte));
            celda.setBackgroundColor(BaseColor.DARK_GRAY);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setVerticalAlignment(Element.ALIGN_BOTTOM);
            celda.setPaddingRight(10f);
            tablaEncabezado.addCell(celda);
            celda = new PdfPCell(new Phrase(subTitulo, LetrasReportes.letraReporte));
            celda.setBackgroundColor(BaseColor.DARK_GRAY);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setPaddingLeft(10f);
            tablaEncabezado.addCell(celda);
            celda = new PdfPCell(new Phrase(hora, LetrasReportes.letraReporte));
            celda.setBackgroundColor(BaseColor.DARK_GRAY);
            celda.setBorder(Rectangle.NO_BORDER);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celda.setPaddingRight(10f);
            tablaEncabezado.addCell(celda);
            parrafo.setAlignment(Element.ALIGN_CENTER);
            parrafo.add(tablaEncabezado);
            parrafo.setSpacingAfter(30);
            documento.add(parrafo);
        }catch(Exception e){
            Log.e("agregarTitulo", e.toString());
        }
    }

    private void agregarParrafosHijos(Paragraph parrafoHijo){
        parrafoHijo.setAlignment(Element.ALIGN_CENTER);
        parrafo.add(parrafoHijo);
    }

    public void agregarParrafo(String texto){
        try{
            parrafo = new Paragraph(texto, LetrasReportes.letraNormal);
            parrafo.setSpacingAfter(5);
            parrafo.setSpacingBefore(5);
            documento.add(parrafo);
        }catch(Exception e){
            Log.e("agregarParrafo", e.toString());
        }
    }

    public void crearTabla(String[] encabezado, ArrayList<ReporteLibrosxPais> listaReporteLibrosxPais){

        try{

            parrafo = new Paragraph();
            parrafo.setFont(LetrasReportes.letraNormal);
            parrafo.setIndentationLeft(10);
            PdfPTable tablaPDF = new PdfPTable(encabezado.length);
            tablaPDF.setWidths(new int[]{50, 80, 40});
            tablaPDF.setWidthPercentage(100);
            PdfPCell celda;
            int indiceColumna = 0;
            while(indiceColumna < encabezado.length){
                celda = new PdfPCell(new Phrase(encabezado[indiceColumna++], LetrasReportes.letraCabeceraTabla));
                //celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBackgroundColor(BaseColor.GRAY);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setPaddingLeft(10f);
                celda.setFixedHeight(40);
                tablaPDF.addCell(celda);
            }

            int total = 0;
            for(int indiceFila = 0; indiceFila < listaReporteLibrosxPais.size(); indiceFila++){
                String autor = listaReporteLibrosxPais.get(indiceFila).getPais();
                String cantidad = listaReporteLibrosxPais.get(indiceFila).getCantidad();
                ArrayList<String> listatitulos = listaReporteLibrosxPais.get(indiceFila).getTitulos();
                total = total + Integer.parseInt(cantidad);
                System.out.println("AUTOR: "+autor+" TÍTULOS: "+listatitulos.toString()+
                        " CANTIDAD: "+cantidad+ " TOTAL: "+total);

                String[] filaDetalle = {autor, listatitulos.toString(), cantidad};

                for(int indiceColumnas = 0; indiceColumnas < encabezado.length; indiceColumnas++){

                    celda = new PdfPCell(new Phrase(filaDetalle[indiceColumnas].toUpperCase(), LetrasReportes.letraDatosTabla));

                    if(indiceColumnas == 1){
                        String libro;
                        List lista = new List();
                        for (int i = 0; i < listatitulos.size(); i++) {
                            libro = String.valueOf(listatitulos.get(i));
                            System.out.println("ELEMENTO LISTA: "+libro);
                            lista.add(new ListItem(libro.toUpperCase()));
                        }
                        Phrase titulo = new Phrase();
                        titulo.add(lista);
                        celda.addElement(titulo);
                        celda.setPaddingLeft(10f);
                        celda.setPaddingTop(0.5f);
                        celda.setPaddingBottom(5f);
                        //celda.setUseAscender(true);
                        //celda.setUseDescender(true);
                        //tablaPDF.addCell(celda);
                    }

                    if(indiceColumnas == 2){
                        celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        celda.setPaddingRight(10f);
                    }
                    //celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //celda.setUseAscender(true);
                    celda.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    //celda.setFixedHeight(40);
                    celda.setPaddingLeft(10f);
                    tablaPDF.addCell(celda);
                }
            }

            parrafo.add(tablaPDF);
            documento.add(parrafo);

            Paragraph parrafoPie = new Paragraph();
            parrafoPie.setIndentationLeft(10);
            PdfPTable tablaPie = new PdfPTable(2);
            tablaPie.setWidths(new int[]{130, 40});
            tablaPie.setWidthPercentage(100);
            PdfPCell celdaPie;
            celdaPie = new PdfPCell(new Phrase("TOTAL LIBROS", LetrasReportes.letraCabeceraTabla));
            celdaPie.setFixedHeight(40);
            celdaPie.setPaddingLeft(10f);
            celdaPie.setBackgroundColor(BaseColor.DARK_GRAY);
            celdaPie.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tablaPie.addCell(celdaPie);
            celdaPie = new PdfPCell(new Phrase(String.valueOf(total), LetrasReportes.letraCabeceraTabla));
            celdaPie.setFixedHeight(40);
            celdaPie.setPaddingRight(10f);
            celdaPie.setBackgroundColor(BaseColor.GRAY);
            celdaPie.setHorizontalAlignment(Element.ALIGN_RIGHT);
            celdaPie.setVerticalAlignment(Element.ALIGN_MIDDLE);
            tablaPie.addCell(celdaPie);
            parrafoPie.add(tablaPie);
            documento.add(parrafoPie);

        }catch(Exception e){
            Log.e("crearTabla", e.toString());
        }
    }

    public void verPDF(){
        Bundle datos = new Bundle();
        datos.putString("Ruta", archivoPDF.getAbsolutePath());
        Intent intento = new Intent(contexto, VisorPDF.class);
        //intento.putExtra("Ruta", archivoPDF.getAbsolutePath());
        intento.putExtras(datos);
        intento.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        contexto.startActivity(intento);
    }
}
