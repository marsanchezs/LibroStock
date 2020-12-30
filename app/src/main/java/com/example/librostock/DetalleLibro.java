package com.example.librostock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.Objetos.Libro;

import java.util.ArrayList;
import java.util.Objects;

public class DetalleLibro extends AppCompatActivity {

    private Bundle datosLibro;
    private Context contexto;
    private String titulo, autor, genero, actividad;
    private TextView txtTitulo, txtTit, txtAut, txtGen;
    private Toolbar toolbar;
    private LibroStockDelegar delegar = new LibroStockDelegar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);

        recibirDatos();

        contexto = getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.miBarraHerramientasDL);
        txtTitulo = (TextView) findViewById(R.id.barraHerramientasTitulo);
        txtTit = (TextView) findViewById(R.id.txtTituloDL);
        txtAut = (TextView) findViewById(R.id.txtAutorDL);
        txtGen = (TextView) findViewById(R.id.txtGeneroDL);
        //datos = (TextView) findViewById(R.id.txtDatosDL);
        cargarToolbar();
        cargarDatos();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver(null);
            }
        });
    }

    //MÉTODOS
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("HardwareIds")
    private void cargarToolbar(){
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        txtTitulo.setText(R.string.tituloDetalleLibro);
        ((TextView) txtTitulo).setTextSize(30);
    }

    private void recibirDatos(){
        datosLibro = this.getIntent().getExtras();
        assert datosLibro != null;
        titulo = datosLibro.getString("Titulo");
        autor = datosLibro.getString("Autor");
        genero = datosLibro.getString("Genero");
        actividad = datosLibro.getString("Actividad");
    }

    private void cargarDatos(){
        txtTit.setText(titulo);
        txtAut.setText(autor);
        txtGen.setText(genero);
    }

    private void editarLibro(){
        //Toast.makeText(contexto, "EDITAR", Toast.LENGTH_LONG).show();
        editar(titulo, autor, genero);
    }

    private void editar(final String titulo, final String autor, final String genero){
        final AlertDialog dialogoEditar;
        final AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(DetalleLibro.this);
        LayoutInflater inflador = getLayoutInflater();
        View vistaDialogo = inflador.inflate(R.layout.dialogo_editar,null);
        constructorDialogo.setView(vistaDialogo);
        dialogoEditar = constructorDialogo.create();
        final EditText edtTitulo = (EditText) vistaDialogo.findViewById(R.id.edtTituloLibroDED);
        final AutoCompleteTextView edtAutor = (AutoCompleteTextView) vistaDialogo.findViewById(R.id.edtAutorLibroDED);
        final AutoCompleteTextView edtGenero = (AutoCompleteTextView) vistaDialogo.findViewById(R.id.edtGeneroLibroDED);
        final ImageView btnLimpiarTitulo = (ImageView) vistaDialogo.findViewById(R.id.imgLimpiarTituloDED);
        final ImageView btnLimpiarAutor = (ImageView) vistaDialogo.findViewById(R.id.imgLimpiarAutorDED);
        final ImageView btnLimpiarGenero = (ImageView) vistaDialogo.findViewById(R.id.imgLimpiarGeneroDED);
        ImageButton btnConfirmar = (ImageButton) vistaDialogo.findViewById(R.id.btnConfirmarDED);
        ImageButton btnCancelar = (ImageButton) vistaDialogo.findViewById(R.id.btnCancelarDED);
        edtTitulo.setText(titulo);
        edtAutor.setText(autor);
        edtGenero.setText(genero);
        traerAutores(edtAutor, edtGenero);
        traerGeneros(edtGenero);

        btnLimpiarTitulo.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarTitulo(edtTitulo, btnLimpiarTitulo);
            }
        });

        mostrarBotonLimpiarTitulo(edtTitulo, btnLimpiarTitulo);

        btnLimpiarAutor.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarAutor(edtAutor, btnLimpiarAutor);
            }
        });

        mostrarBotonLimpiarAutor(edtAutor, btnLimpiarAutor);

        btnLimpiarGenero.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarGenero(edtGenero, btnLimpiarGenero);
            }
        });

        mostrarBotonLimpiarGenero(edtGenero, btnLimpiarGenero);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                validarLibro(edtTitulo, edtAutor, edtGenero, titulo, autor, genero, dialogoEditar);

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                dialogoEditar.dismiss();
            }
        });
        dialogoEditar.show();

    }

    private void validarLibro(EditText edtTitulo, AutoCompleteTextView edtAutor, AutoCompleteTextView edtGenero,
                              String tituloActual, String autorActual, String generoActual, AlertDialog dialogoEditar){
        String mensaje = "SIN MENSAJE";
        String titulo = edtTitulo.getText().toString();
        String autor = edtAutor.getText().toString();
        String genero = edtGenero.getText().toString();
        if((titulo.isEmpty()) && (autor.isEmpty()) && (genero.isEmpty())){
            mensaje = "DEBE INGRESAR TODOS LOS DATOS";
            Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
        }else if(titulo.isEmpty()){
            mensaje = "INGRESAR TÍTULO";
            edtTitulo.setError(mensaje);
        }else if(autor.isEmpty()){
            mensaje = "INGRESAR AUTOR";
            edtAutor.setError(mensaje);
        }else if(genero.isEmpty()){
            mensaje = "INGRESAR GÉNERO";
            edtGenero.setError(mensaje);
        }else{
            generarLibro(titulo, autor, genero, tituloActual, autorActual, generoActual, dialogoEditar);
        }

    }

    private void generarLibro(String titulo, String autor, String genero,
                              String tituloActual, String autorActual, String generoActual,
                              AlertDialog dialogoEditar){
        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setGenero(genero);
        validarLibro(libro, tituloActual, autorActual, generoActual, dialogoEditar);
    }

    private void validarLibro(Libro libro, String tituloActual, String autorActual, String generoActual,
                              AlertDialog dialogoEditar){
        String mensaje = "";
        String respuestaValidarLibro = delegar.validarLibro(contexto, libro);
        if(respuestaValidarLibro.equals("LIBRO EXISTE")){
            mensaje = libro.getTitulo().toUpperCase()+"  DE "+libro.getAutor().toUpperCase()+" YA SE ENCUENTRA AGREGADO";
            Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
        }else{
            String respuestaActualizarLibro = delegar.actualizarLibro(contexto, libro,
                    tituloActual, autorActual, generoActual);
            if(respuestaActualizarLibro.equals("OK")){
                mensaje = "LIBRO EDITADO CON ÉXITO";
                Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
                dialogoEditar.dismiss();
                mostrarLibroActualizado(libro);
                //volverMainActivity(null);
            }else{
                Toast.makeText(contexto, respuestaActualizarLibro, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void mostrarLibroActualizado(Libro libro){
        txtTit.setText(libro.getTitulo());
        txtAut.setText(libro.getAutor());
        txtGen.setText(libro.getGenero());
    }

    private void mostrarBotonLimpiarTitulo(final EditText edtTitulo, final ImageView imgLimpiarTitulo){
        imgLimpiarTitulo.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarTitulo(edtTitulo, imgLimpiarTitulo);
            }
        });

        edtTitulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtTitulo.getText().toString().length() == 0){
                    imgLimpiarTitulo.setVisibility(View.GONE);
                }else{
                    imgLimpiarTitulo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void limpiarTitulo(EditText edtTitulo, ImageView imgLimpiarTitulo){
        edtTitulo.setText("");
        edtTitulo.requestFocus();
        imgLimpiarTitulo.setVisibility(View.GONE);
    }

    private void mostrarBotonLimpiarAutor(final EditText edtAutor, final ImageView imgLimpiarAutor){
        imgLimpiarAutor.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarAutor(edtAutor, imgLimpiarAutor);
            }
        });

        edtAutor.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtAutor.getText().toString().length() == 0){
                    imgLimpiarAutor.setVisibility(View.GONE);
                }else{
                    imgLimpiarAutor.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void limpiarAutor(EditText edtAutor, ImageView imgLimpiarAutor){
        edtAutor.setText("");
        edtAutor.requestFocus();
        imgLimpiarAutor.setVisibility(View.GONE);
    }

    private void mostrarBotonLimpiarGenero(final EditText edtGenero, final ImageView imgLimpiarGenero){
        imgLimpiarGenero.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarGenero(edtGenero, imgLimpiarGenero);
            }
        });

        edtGenero.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtGenero.getText().toString().length() == 0){
                    imgLimpiarGenero.setVisibility(View.GONE);
                }else{
                    imgLimpiarGenero.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void limpiarGenero(EditText edtGenero, ImageView imgLimpiarGenero){
        edtGenero.setText("");
        edtGenero.requestFocus();
        imgLimpiarGenero.setVisibility(View.GONE);
    }

    private void traerAutores(AutoCompleteTextView edtAutores, final AutoCompleteTextView edtGenero) {
        ArrayList<String> autores = delegar.traerAutores(contexto);
        System.out.println("LISTA AUTORES: " + autores.toString());
        String[] arrayAutores = new String[autores.size()];
        arrayAutores = autores.toArray(arrayAutores);
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(contexto, R.layout.lista_desplegable, R.id.txt, arrayAutores);
        edtAutores.setAdapter(adaptador);

        edtAutores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                edtGenero.requestFocus();


            }
        });

    }

    private void traerGeneros(AutoCompleteTextView edtGenero) {
        ArrayList<String> generos = delegar.traerGeneros(contexto);
        System.out.println("LISTA GENEROS: " + generos.toString());
        String[] arrayGeneros = new String[generos.size()];
        arrayGeneros = generos.toArray(arrayGeneros);
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(contexto, R.layout.lista_desplegable, R.id.txt, arrayGeneros);
        edtGenero.setAdapter(adaptador);

        edtGenero.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //btnAgregarLibro.requestFocus();

            }
        });

    }

    private void eliminarLibro(){
        eliminar(titulo, autor, genero);
    }

    private void eliminar(final String titulo, final String autor, final String genero){
        final AlertDialog dialogoEliminar;
        final AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(DetalleLibro.this);
        LayoutInflater inflador = getLayoutInflater();
        View vistaDialogo = inflador.inflate(R.layout.dialogo_eliminar,null);
        constructorDialogo.setView(vistaDialogo);
        dialogoEliminar = constructorDialogo.create();
        TextView txvMensaje = (TextView) vistaDialogo.findViewById(R.id.txtMensajeDE);
        ImageButton btnConfirmar = (ImageButton) vistaDialogo.findViewById(R.id.btnConfirmarDE);
        ImageButton btnCancelar = (ImageButton) vistaDialogo.findViewById(R.id.btnCancelarDE);
        String mensaje = "¿ESTÁ SEGURO DE ELIMINAR "+titulo.toUpperCase()+" DE "+autor.toUpperCase()+"?";
        txvMensaje.setText(mensaje);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String msg = "";
                String respuestaBorrarLibro = delegar.borrarLibro(contexto, titulo, autor, genero);
                if(respuestaBorrarLibro.equals("OK")){
                    msg = titulo.toUpperCase()+" DE "+autor.toUpperCase()+" HA SIDO ELIMINADO";
                    Toast.makeText(contexto, msg, Toast.LENGTH_SHORT).show();
                    volver(null);
                    dialogoEliminar.dismiss();
                }else{
                    msg = "NO SE PUDO ELIMINAR "+titulo.toUpperCase()+ " DE "+autor.toUpperCase();
                    Toast.makeText(contexto, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                dialogoEliminar.dismiss();
            }
        });
        dialogoEliminar.show();

    }

    public void volver(View vista){
        //Context contexto = getApplicationContext();
        Intent intento = null;
        if(actividad.equals("frListaLibros")){
            intento = new Intent(contexto, MainActivity.class);
        }else if(actividad.equals("BuscarLibro")){
            intento = new Intent(contexto, BuscarLibro.class);
        }
        //Intent intento = new Intent(contexto, MainActivity.class);
        startActivity(intento);
    }

    @Override public boolean onCreateOptionsMenu(Menu miMenu){
        getMenuInflater().inflate(R.menu.mi_menu2, miMenu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem opcionMenu){
        int id = opcionMenu.getItemId();
        switch(opcionMenu.getItemId()){
            case R.id.menuEditar:
                editarLibro();
                return true;
            case R.id.menuEliminar:
                eliminarLibro();
                return true;
            default:
                return super.onOptionsItemSelected(opcionMenu);
        }
    }
}