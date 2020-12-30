package com.example.librostock;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.Informes.ModeloPDF;
import com.example.librostock.Informes.ReporteAutor;
import com.example.librostock.Informes.ReporteGeneral;
import com.example.librostock.Informes.ReporteGenero;
import com.example.librostock.Informes.ReportePais;
import com.example.librostock.Objetos.Libro;
import com.example.librostock.Objetos.ReporteLibrosxPais;
import com.example.librostock.Utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Objects;

public class Reportes extends AppCompatActivity {

    private Context contexto;
    private TextView txtTitulo;
    private Toolbar toolbar;
    private Utilidades utilidades = new Utilidades();
    private LibroStockDelegar delegar = new LibroStockDelegar();
    private ArrayList<Libro> listaReporteGeneral;
    private ArrayList<String[]> listaReporteAutor;
    private Spinner spTipoReporte;
    private ImageButton btnPDF;
    private ModeloPDF modeloPDF;
    private ReporteGeneral reporteGeneral;
    private ReporteAutor reporteAutor;
    private ReporteGenero reporteGenero;
    private ReportePais reportePais;
    private String[] encabezado = {"TÍTULO", "AUTOR", "GÉNERO"};
    private String textoCorto = "Hola", fecha;
    private String textLargo = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        contexto = getApplicationContext();
        spTipoReporte = (Spinner) findViewById(R.id.spTipoReporteR);
        btnPDF = (ImageButton) findViewById(R.id.btnPDFR);
        txtTitulo = (TextView) findViewById(R.id.barraHerramientasTitulo);
        toolbar = (Toolbar) findViewById(R.id.miBarraHerramientasR);
        cargarToolbar();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver(null);
            }
        });

        fecha = utilidades.traerFecha();
        //listaReporteGeneral = delegar.reporteGeneral(contexto);
        //listaReporteAutor = delegar.reporteAutor(contexto);

        otorgarPermisos();
        cargarSpinner();
        //cargarPDF();

        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validarReporte();
            }
        });
    }

    //MÉTODOS
    private void validarReporte(){
        String tipoReporte = spTipoReporte.getSelectedItem().toString();

        switch(tipoReporte){
            case "Seleccione...":
                Toast.makeText(contexto, "DEBE SELECCIONAR UN REPORTE", Toast.LENGTH_LONG).show();
                break;
            case "Reporte General":
                generarReporte("Reporte General");
                break;
            case "Reporte Autor":
                generarReporte("Reporte Autor");
                break;
            case "Reporte Género":
                generarReporte("Reporte Género");
                break;
            case "Reporte País":
                generarReporte("Reporte País");
                break;
        }
    }

    private void generarReporte(String tipoReporte){

        switch(tipoReporte){
            case "Reporte General":
                cargarReporteGeneral();
                reporteGeneral.verPDF();
                break;
            case "Reporte Autor":
                cargarReporteAutor();
                reporteAutor.verPDF();
                break;
            case "Reporte Género":
                cargarReporteGenero();
                reporteGenero.verPDF();
                break;
            case "Reporte País":
                cargarReportePais();
                reportePais.verPDF();
                break;
        }

    }

    private void cargarSpinner(){
        String[] tipoReporte = new String[]{"Seleccione...", "Reporte General", "Reporte Autor", "Reporte Género", "Reporte País"};
        ArrayAdapter<String> adaptador;
        adaptador = new ArrayAdapter<String>(contexto, R.layout.lista_desplegable, R.id.txt, tipoReporte);
        spTipoReporte.setAdapter(adaptador);
    }

    private void cargarReporteGeneral(){
        reporteGeneral = new ReporteGeneral(contexto);
        reporteGeneral.abrirDocumentoPDF();
        reporteGeneral.cerrarDocumento();
    }

    private void cargarReporteAutor(){
        reporteAutor = new ReporteAutor(contexto);
        reporteAutor.abrirDocumentoPDF();
        reporteAutor.cerrarDocumento();
    }

    private void cargarReporteGenero(){
        reporteGenero = new ReporteGenero(contexto);
        reporteGenero.abrirDocumentoPDF();
        reporteGenero.cerrarDocumento();
    }

    private void cargarReportePais(){
        reportePais = new ReportePais(contexto);
        reportePais.abrirDocumentoPDF();
        reportePais.cerrarDocumento();
    }

    private void cargarPDF(){
        modeloPDF = new ModeloPDF(getApplicationContext());
        modeloPDF.abrirDocumentoPDF();
        modeloPDF.agregarMetadatos("Reporte General", "Detalle Libros", "LibroStock");
        modeloPDF.agregarTitulos("Reporte General", "Detalle Libros", fecha);
        modeloPDF.agregarParrafo(textoCorto);
        modeloPDF.agregarParrafo(textLargo);
        //modeloPDF.crearTabla(encabezado, traerClientes());
        modeloPDF.crearTabla2(encabezado, listaReporteGeneral);
        modeloPDF.cerrarDocumento();
    }

    private void otorgarPermisos(){
        // Permisos
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,},
                    1000);
        }
    }

    public void verPDF(View vista){
        modeloPDF.verPDF();
    }

    private ArrayList<String[]> traerClientes(){
        ArrayList<String[]> filas = new ArrayList<>();
        filas.add(new String[]{"1", "Esteban Efraín", "Paredes"});
        filas.add(new String[]{"2", "Marcelo Pablo", "Barticciotto"});
        filas.add(new String[]{"3", "Rubén", "Martínez"});
        return filas;
    }

    private void cargarToolbar(){
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        txtTitulo.setText(R.string.reportes);
        ((TextView) txtTitulo).setTextSize(30);
    }

    public void volver(View vista){
        Intent intento = new Intent(contexto, MainActivity.class);
        startActivity(intento);
    }
}