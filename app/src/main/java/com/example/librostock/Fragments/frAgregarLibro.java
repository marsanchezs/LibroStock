package com.example.librostock.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.Objetos.Autor;
import com.example.librostock.Objetos.Libro;
import com.example.librostock.R;

import java.util.ArrayList;

public class frAgregarLibro extends Fragment {

    private AutoCompleteTextView edtAutorLibro, edtGeneroLibro;
    private Button btnAgregarLibro, btnCamara;
    private EditText edtTituloLibro;
    private ImageView imgLimpiarTitulo, imgLimpiarAutor, imgLimpiarGenero, imgLibro;
    private LibroStockDelegar delegar = new LibroStockDelegar();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_fr_agregar_libro, container, false);

        edtTituloLibro = (EditText) vista.findViewById(R.id.edtTituloLibroFRAL);
        edtAutorLibro = (AutoCompleteTextView) vista.findViewById(R.id.edtAutorLibroFRAL);
        edtGeneroLibro = (AutoCompleteTextView) vista.findViewById(R.id.edtGeneroLibroFRAL);
        imgLimpiarTitulo = (ImageView) vista.findViewById(R.id.imgLimpiarTituloFRAL);
        imgLimpiarAutor = (ImageView) vista.findViewById(R.id.imgLimpiarAutorFRAL);
        imgLimpiarGenero = (ImageView) vista.findViewById(R.id.imgLimpiarGeneroFRAL);
        imgLibro = (ImageView) vista.findViewById(R.id.imgLibroFRAL);
        btnCamara = (Button) vista.findViewById(R.id.btnCamaraFRAL);
        btnAgregarLibro = (Button) vista.findViewById(R.id.btnAgregarFRAL);

        edtTituloLibro.requestFocus();

        btnAgregarLibro.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                validar();
                //agregarLibro2();
            }
        });

        traerAutores();
        traerGeneros();
        mostrarBotonesLimpiar();

        // Inflate the layout for this fragment
        return vista;
    }

    //MÉTODOS
    private void permisos(){
        Context contexto = getActivity();
        assert contexto != null;
        if (ContextCompat.checkSelfPermission(contexto, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contexto, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
    }

    private void mostrarBotonesLimpiar(){
        mostrarBotonLimpiarTitulo();
        mostrarBotonLimpiarAutor();
        mostrarBotonLimpiarGenero();
    }

    private void mostrarBotonLimpiarGenero(){
        imgLimpiarGenero.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarGenero();
            }
        });

        edtGeneroLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imgLimpiarGenero.setVisibility(View.VISIBLE);
            }
        });

        edtGeneroLibro.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtGeneroLibro.getText().toString().length() == 0){
                    imgLimpiarGenero.setVisibility(View.GONE);
                }else{
                    imgLimpiarGenero.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void limpiarGenero(){
        edtGeneroLibro.setText("");
        edtGeneroLibro.requestFocus();
        imgLimpiarGenero.setVisibility(View.GONE);
    }

    private void mostrarBotonLimpiarAutor(){
        imgLimpiarAutor.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarAutor();
            }
        });

        edtAutorLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imgLimpiarAutor.setVisibility(View.VISIBLE);
            }
        });

        edtAutorLibro.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtAutorLibro.getText().toString().length() == 0){
                    imgLimpiarAutor.setVisibility(View.GONE);
                }else{
                    imgLimpiarAutor.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void limpiarAutor(){
        edtAutorLibro.setText("");
        edtAutorLibro.requestFocus();
        imgLimpiarAutor.setVisibility(View.GONE);
    }

    private void mostrarBotonLimpiarTitulo(){
        imgLimpiarTitulo.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarTitulo();
            }
        });

        edtTituloLibro.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtTituloLibro.getText().toString().length() == 0){
                    imgLimpiarTitulo.setVisibility(View.GONE);
                }else{
                    imgLimpiarTitulo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void limpiarTitulo(){
        edtTituloLibro.setText("");
        edtTituloLibro.requestFocus();
        imgLimpiarTitulo.setVisibility(View.GONE);
    }

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
        String titulo = edtTituloLibro.getText().toString();
        String autor = edtAutorLibro.getText().toString();
        String genero = edtGeneroLibro.getText().toString();
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setGenero(genero);
        validarLibro(libro);
    }

    private void validarLibro(Libro libro){
        Context contexto = getActivity();
        String mensaje = "";
        String respuestaValidarLibro = delegar.validarLibro(contexto, libro);
        if(respuestaValidarLibro.equals("LIBRO EXISTE")){
            mensaje = libro.getTitulo().toUpperCase()+"  DE "+libro.getAutor().toUpperCase()+" YA SE ENCUENTRA AGREGADO";
            Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
        }else{
            String respuestaAgregarLibro = delegar.agregarLibro(contexto, libro);
            if(respuestaAgregarLibro.equals("OK")){
                mensaje = libro.getTitulo().toUpperCase()+" DE "+libro.getAutor().toUpperCase()+" HA SIDO AGREGADO";
                Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
                limpiar();
            }else{
                Toast.makeText(contexto, respuestaAgregarLibro, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void traerAutores() {
        Context contexto = getActivity();
        ArrayList<String> autores = delegar.traerAutores(contexto);
        System.out.println("LISTA AUTORES: " + autores.toString());
        String[] arrayAutores = new String[autores.size()];
        arrayAutores = autores.toArray(arrayAutores);
        assert contexto != null;
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(contexto, R.layout.lista_desplegable, R.id.txt, arrayAutores);
        edtAutorLibro.setAdapter(adaptador);

        edtAutorLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtGeneroLibro.requestFocus();
            }
        });

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
                btnAgregarLibro.requestFocus();

            }
        });

    }

    private void limpiar(){
        edtTituloLibro.setText("");
        edtAutorLibro.setText("");
        edtGeneroLibro.setText("");
        edtTituloLibro.requestFocus();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            Log.i("ACTUALIZADO", "OK");
        }
    }
}