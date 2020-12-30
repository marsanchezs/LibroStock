package com.example.librostock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.librostock.Adaptadores.AdaptadorFilaLibro;
import com.example.librostock.Adaptadores.AdaptadorRecycler;
import com.example.librostock.BBDD.LibroStockConstantes;
import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.BBDD.LibroStockOpenHelper;
import com.example.librostock.Objetos.Libro;

import java.util.ArrayList;
import java.util.Objects;

public class BuscarLibro extends AppCompatActivity implements AdaptadorRecycler.clickLibro{

    private static String NOMBRE_BBDD = LibroStockConstantes.NOMBRE_BBDD;
    private static int VERSION_BBDD = LibroStockConstantes.VERSION_BBDD;
    private LibroStockDelegar delegar = new LibroStockDelegar();
    private Toolbar toolbarBuscar;
    private AutoCompleteTextView edtBuscar;
    private ImageView btnLimpiarBuscar;
    private LinearLayout ll1, ll2;
    private RecyclerView rvListaLibros;
    private AdaptadorRecycler adaptador;
    private Toast miMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_libro);

        toolbarBuscar = (Toolbar) findViewById(R.id.toolbarBuscar);
        edtBuscar = (AutoCompleteTextView) findViewById(R.id.edtBuscarLibroBHB);
        btnLimpiarBuscar = (ImageView) findViewById(R.id.imgLimpiarBuscarBHB);
        ll1 = (LinearLayout) findViewById(R.id.llvFRLL1);
        ll2 = (LinearLayout) findViewById(R.id.llvFRLL2);
        rvListaLibros = (RecyclerView) findViewById(R.id.rvListaLibrosFRLL);
        LinearLayoutManager admDisenoLineal = new LinearLayoutManager(getApplicationContext());
        rvListaLibros.setLayoutManager(admDisenoLineal);
        cargarToolbar();

        toolbarBuscar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(BuscarLibro.this, MainActivity.class);
                startActivity(intento);
            }
        });

        //btnBuscar.setOnClickListener(this);
        //traerLibros();

        //cargarListView();
        cargarRecyclerView();

        filtarLibro(edtBuscar);
    }

    //MÉTODOS
    private void filtarLibro(final AutoCompleteTextView edtBuscar){
        btnLimpiarBuscar.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                edtBuscar.setText("");
            }
        });

        edtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
                if(edtBuscar.getText().toString().length() == 0){
                    btnLimpiarBuscar.setVisibility(View.GONE);
                }else{
                    btnLimpiarBuscar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void filtrar(String texto) {
        ArrayList<Libro> filtrarLista = new ArrayList<>();
        ArrayList<Libro> lista = traerLibros(getApplicationContext());

        for(Libro libro : lista) {
            if(libro.getTitulo().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(libro);
            }
        }

        adaptador.filtrar(filtrarLista);
    }

    private void cargarRecyclerView(){
        ArrayList<Libro> lista = traerLibros(getApplicationContext());

        if(lista.size() == 0){
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
        }else{
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            System.out.println("LISTA DE LIBROS RV: "+lista);
            adaptador = new AdaptadorRecycler(lista, this);
            rvListaLibros.setAdapter(adaptador);
        }
    }

    private ArrayList<Libro> traerLibros(Context contexto){
        LibroStockOpenHelper adm = new LibroStockOpenHelper(contexto, NOMBRE_BBDD, null, VERSION_BBDD);
        SQLiteDatabase bd = adm.getWritableDatabase();
        //TABLA LIBROS
        //db.execSQL("CREATE TABLE LIBROS (Id INTEGER PRIMARY KEY AUTOINCREMENT, TITULO TEXT, AUTOR TEXT, GENERO TEXT) ");
        ArrayList<Libro> listaLibros = new ArrayList<>();
        String consulta = "SELECT TITULO, AUTOR, GENERO FROM LIBROS ORDER BY TITULO";
        Cursor fila = null;
        try{
            Log.e("CONSULTA: ", consulta);
            fila = bd.rawQuery(consulta, null);

            if (fila.moveToFirst()) {
                do {
                    Libro libro = new Libro();
                    libro.setTitulo(fila.getString(0));
                    libro.setAutor(fila.getString(1));
                    libro.setGenero(fila.getString(2));
                    listaLibros.add(libro);
                } while (fila.moveToNext());
            }

        }catch (Exception e){
            String mensaje = "NO SE OBTUVIERON LOS LIBROS";
            Log.e("ERROR LIBROS", mensaje, e);
        }finally {
            assert fila != null;
            fila.close();
            bd.close();
        }
        return listaLibros;
    }

    private void cargarToolbar(){
        setSupportActionBar(toolbarBuscar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        edtBuscar.requestFocus();
    }

    @Override
    public void clickListaLibro(int clickedLibro) {
        ArrayList<Libro> lista = traerLibros(getApplicationContext());
        if(miMensaje != null){
            miMensaje.cancel();
        }
        String titulo = lista.get(clickedLibro).getTitulo();
        String autor = lista.get(clickedLibro).getAutor();
        String genero = lista.get(clickedLibro).getGenero();

        irDetalleLibro(titulo, autor, genero);

    }

    @Override
    public void clickLargoListaLibro(int clickedLibro) {

    }

    private void irDetalleLibro(String titulo, String autor, String genero){
        Bundle datosLibro = new Bundle();
        datosLibro.putString("Titulo", titulo);
        datosLibro.putString("Autor", autor);
        datosLibro.putString("Genero", genero);
        datosLibro.putString("Actividad", "BuscarLibro");
        Context contexto = getApplicationContext();
        Intent intento = new Intent(contexto, DetalleLibro.class);
        intento.putExtras(datosLibro);
        startActivity(intento);
    }

    /*public void cargarListView(){
        Context contexto = getApplicationContext();
        ArrayList<Libro> lista = delegar.traerLibros(contexto);
        capturarFila(lista);
        System.out.println("LISTA DE LIBROS: "+lista);
        AdaptadorFilaLibro miAdaptador = new AdaptadorFilaLibro(contexto, lista);
        lvListaLibros.setAdapter(miAdaptador);
    }

    @Override
    public void onClick(View view) {
        Context contexto = getApplicationContext();
        if(view.getId() == R.id.btnBuscarLibroBHB){
            Toast.makeText(contexto, "FUNCIÓN", Toast.LENGTH_SHORT).show();
        }
    }

    private void traerLibros() {
        Context contexto = getApplicationContext();
        ArrayList<String> libros = delegar.traerLibro(contexto);
        System.out.println("LISTA LIBROS: " + libros.toString());
        String[] arrayLibros = new String[libros.size()];
        arrayLibros = libros.toArray(arrayLibros);
        assert contexto != null;
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(contexto, R.layout.lista_desplegable, R.id.txt, arrayLibros);
        edtBuscar.setAdapter(adaptador);

        edtBuscar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });
    }

    private void capturarFila(final ArrayList<Libro> listaLibros){
        lvListaLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Context contexto = getApplicationContext();
                String titulo, autor, genero;
                titulo = listaLibros.get(position).getTitulo();
                autor = listaLibros.get(position).getAutor();
                genero = listaLibros.get(position).getGenero();
                String mensaje = "TÍTULO: "+titulo+" AUTOR: "+autor+" GÉNERO: "+genero;
                Toast.makeText(contexto, mensaje, Toast.LENGTH_LONG).show();

            }
        });
    }*/
}