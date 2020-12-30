package com.example.librostock.Informes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.example.librostock.Objetos.Libro;
import com.example.librostock.VisorPDF;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
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

public class ModeloPDF {

    private static final String NOMBRE_DIRECTORIO = "Reportes";
    private Context contexto;
    private File archivoPDF;
    private Document documento;
    private PdfWriter pdfEscritor;
    private Paragraph parrafo;
    private Font letraTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font letraSubTitulo = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font letraNormal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font letraResaltada = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);

    public ModeloPDF(Context contexto) {
        this.contexto = contexto;
    }

    public File getRuta() {
        File ruta = null;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), NOMBRE_DIRECTORIO);
            if(ruta != null) {
                if(!ruta.mkdirs()) {
                    if(!ruta.exists()) {
                        return null;
                    }
                }
            }
        }
        return ruta;
    }

    public void crearFichero() {
        File ruta = getRuta();
        if(ruta != null) {
            archivoPDF = new File(ruta, "ReporteGeneral.pdf");
        }
    }

    public void abrirDocumentoPDF(){
        crearArchivo();
        //crearFichero();
        try{
            documento = new Document(PageSize.LETTER);
            pdfEscritor = PdfWriter.getInstance(documento, new FileOutputStream(archivoPDF));
            documento.open();

        }catch(Exception e){
            Log.e("abrirDocumentoPDF", e.toString());
        }
    }

    private void crearArchivo(){
        File ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File carpeta = new File(ruta.getAbsolutePath(), "PDF");
        if(!carpeta.exists())
            carpeta.mkdirs();
        archivoPDF = new File(carpeta, "ReporteGeneral.pdf");
    }

    public void cerrarDocumento(){
        documento.close();
    }

    public void agregarMetadatos(String titulo, String tema, String autor){
        documento.addTitle(titulo);
        documento.addSubject(tema);
        documento.addAuthor(autor);
    }

    public void agregarTitulos(String titulo, String subTitulo, String fecha){
        try{
            parrafo = new Paragraph();
            PdfPTable tablaEncabezado = new PdfPTable(1);
            PdfPCell celda;
            celda = new PdfPCell(new Phrase(titulo, letraSubTitulo));
            celda.setBackgroundColor(BaseColor.YELLOW);
            celda.setBorder(Rectangle.NO_BORDER);
            tablaEncabezado.addCell(celda);
            celda = new PdfPCell(new Phrase(subTitulo, letraSubTitulo));
            celda.setBackgroundColor(BaseColor.YELLOW);
            celda.setBorder(Rectangle.NO_BORDER);
            tablaEncabezado.addCell(celda);
            parrafo.add(tablaEncabezado);
            //documento.add(parrafo);

            agregarParrafosHijos(new Paragraph(titulo, letraTitulo));
            agregarParrafosHijos(new Paragraph(subTitulo, letraSubTitulo));
            agregarParrafosHijos(new Paragraph("GENERADO: "+fecha, letraNormal));
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
            parrafo = new Paragraph(texto, letraNormal);
            parrafo.setSpacingAfter(5);
            parrafo.setSpacingBefore(5);
            documento.add(parrafo);
        }catch(Exception e){
            Log.e("agregarParrafo", e.toString());
        }
    }

    public void crearTabla(String[] encabezado, ArrayList<String[]> clientes){

        try{

            parrafo = new Paragraph();
            parrafo.setFont(letraNormal);
            PdfPTable tablaPDF = new PdfPTable(encabezado.length);
            tablaPDF.setWidthPercentage(100);
            PdfPCell celda;
            int indiceColumna = 0;
            while(indiceColumna < encabezado.length){
                celda = new PdfPCell(new Phrase(encabezado[indiceColumna++], letraSubTitulo));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBackgroundColor(BaseColor.GREEN);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setFixedHeight(40);
                tablaPDF.addCell(celda);
            }

            for(int indiceFila = 0; indiceFila < clientes.size(); indiceFila++){
                String[] fila = clientes.get(indiceFila);
                for(int indiceColumnas = 0; indiceColumnas < encabezado.length; indiceColumnas++){
                    celda = new PdfPCell(new Phrase(fila[indiceColumnas]));
                    celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //celda.setUseAscender(true);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    celda.setFixedHeight(40);
                    tablaPDF.addCell(celda);
                }
            }

            parrafo.add(tablaPDF);
            documento.add(parrafo);

        }catch(Exception e){
            Log.e("crearTabla", e.toString());
        }
    }

    public void crearTabla2(String[] encabezado, ArrayList<Libro> libros){

        try{

            parrafo = new Paragraph();
            parrafo.setFont(letraNormal);
            parrafo.setIndentationLeft(10);
            PdfPTable tablaPDF = new PdfPTable(encabezado.length);
            tablaPDF.setWidthPercentage(100);
            PdfPCell celda;
            int indiceColumna = 0;
            while(indiceColumna < encabezado.length){
                celda = new PdfPCell(new Phrase(encabezado[indiceColumna++], letraSubTitulo));
                //celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBackgroundColor(BaseColor.GREEN);
                celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                celda.setPaddingLeft(1);
                celda.setFixedHeight(40);
                tablaPDF.addCell(celda);
            }

            for(int indiceFila = 0; indiceFila < libros.size(); indiceFila++){
                String titulo = libros.get(indiceFila).getTitulo();
                String autor = libros.get(indiceFila).getAutor();
                String genero = libros.get(indiceFila).getGenero();
                String[] filaLibro = {titulo.toUpperCase(), autor.toUpperCase(), genero.toUpperCase()};
                for(int indiceColumnas = 0; indiceColumnas < encabezado.length; indiceColumnas++){
                    celda = new PdfPCell(new Phrase(filaLibro[indiceColumnas]));
                    //celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                    //celda.setUseAscender(true);
                    celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    celda.setFixedHeight(40);
                    tablaPDF.addCell(celda);
                }
            }

            parrafo.add(tablaPDF);
            documento.add(parrafo);

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
