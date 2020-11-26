package com.example.librostock.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.R;

import java.util.ArrayList;

public class frAgregarLibro extends Fragment {

    private AutoCompleteTextView edtGeneroLibro;
    private Button btnAgregarLibro;
    private EditText edtTituloLibro, edtAutorLibro;
    private LibroStockDelegar delegar = new LibroStockDelegar();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_fr_agregar_libro, container, false);

        edtTituloLibro = (EditText) vista.findViewById(R.id.edtTituloLibroFRAL);
        edtAutorLibro = (EditText) vista.findViewById(R.id.edtAutorLibroFRAL);
        edtGeneroLibro = (AutoCompleteTextView) vista.findViewById(R.id.edtGeneroLibroFRAL);
        btnAgregarLibro = (Button) vista.findViewById(R.id.btnAgregarFRAL);

        edtTituloLibro.requestFocus();

        btnAgregarLibro.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                validar();
            }
        });

        traerGeneros();

        // Inflate the layout for this fragment
        return vista;
    }

    //MÉTODOS
    private void validar(){
        Context contexto = getActivity();
        String titulo = edtTituloLibro.getText().toString();
        String autor = edtAutorLibro.getText().toString();
        String genero = edtGeneroLibro.getText().toString();
        String mensaje = "SIN MENSAJE";
        if((titulo.isEmpty()) && (autor.isEmpty()) && (genero.isEmpty())){
            mensaje = "DEBE INGRESAR TODOS LOS DATOS";
            Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
        }else if(titulo.isEmpty()){
            mensaje = "INGRESAR TÍTULO";
            edtTituloLibro.setError(mensaje);
        }else if(autor.isEmpty()){
            mensaje = "INGRESAR AUTOR";
            edtAutorLibro.setError(mensaje);
        }else if(genero.isEmpty()){
            mensaje = "INGRESAR GÉNERO";
            edtGeneroLibro.setError(mensaje);
        }else{
            agregarLibro();
        }
    }

    private void agregarLibro(){
        Context contexto = getActivity();
        String titulo = edtTituloLibro.getText().toString();
        String autor = edtAutorLibro.getText().toString();
        String genero = edtGeneroLibro.getText().toString();
        String mensaje = "TÍTULO: "+titulo+" AUTOR: "+autor+" GÉNERO: "+genero;
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void traerGeneros() {
        Context contexto = getActivity();
        ArrayList<String> generos = delegar.traerGeneros(contexto);
        System.out.println("LISTA GENEROS: " + generos.toString());
        String[] arrayGeneros = new String[generos.size()];
        arrayGeneros = generos.toArray(arrayGeneros);
        assert contexto != null;
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(contexto, R.layout.lista_desplegable, R.id.txt, arrayGeneros);
        edtGeneroLibro.setAdapter(adaptador);

        edtGeneroLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*String articulo = adapterView.getItemAtPosition(i).toString();
                String[] partesArticulo = articulo.split("\\|");
                final String codArticulo = partesArticulo[0];
                String bodega = spBodegas.getSelectedItem().toString();
                final String codBodega = delegate.traerCodigoBodega(contexto, bodega, codEmpresa, codProyecto);
                String mensaje = "EMPRESA: " + codEmpresa + " PROYECTO: " + codProyecto + " CÓDIGO: " + codArticulo + " BODEGA: " + codBodega;
                System.out.println(mensaje);
                imgBtnLimpiarArticulo.setVisibility(View.VISIBLE);*/

            }
        });

    }
}