package com.example.librostock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;

import java.util.Objects;

public class BuscarLibro extends AppCompatActivity {

    private Toolbar toolbarBuscar;
    private AutoCompleteTextView edtBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_libro);

        toolbarBuscar = (Toolbar) findViewById(R.id.toolbarBuscar);
        edtBuscar = (AutoCompleteTextView) findViewById(R.id.edtBuscarLibroBHB);
        cargarToolbar();

        toolbarBuscar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(BuscarLibro.this, MainActivity.class);
                startActivity(intento);
            }
        });
    }

    //MÉTODOS
    private void cargarToolbar(){
        setSupportActionBar(toolbarBuscar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        edtBuscar.requestFocus();
    }
}