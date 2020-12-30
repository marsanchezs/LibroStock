package com.example.librostock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.Objects;

public class VisorPDF extends AppCompatActivity {

    private PDFView visorPDF;
    private File archivo;
    private Toolbar toolbar;
    private Context contexto;
    private TextView txtTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_p_d_f);

        contexto = getApplicationContext();
        visorPDF = (PDFView) findViewById(R.id.visorPDF);
        txtTitulo = (TextView) findViewById(R.id.barraHerramientasTitulo);
        toolbar = (Toolbar) findViewById(R.id.miBarraHerramientasVPDF);
        cargarToolbar();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver(null);
            }
        });

        Bundle datos = getIntent().getExtras();
        if(datos != null){
            archivo = new File(Objects.requireNonNull(datos.getString("Ruta")));
        }
        String dato = datos.getString("Ruta");
        System.out.println("RUTA ARCHIVO: "+dato);

        visorPDF.fromFile(archivo)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .load();

    }

    //MÉTODOS
    private void cargarToolbar(){
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        txtTitulo.setText(R.string.reportes);
        ((TextView) txtTitulo).setTextSize(30);
    }

    public void volver(View vista){
        Intent intento = new Intent(contexto, Reportes.class);
        startActivity(intento);
    }
}