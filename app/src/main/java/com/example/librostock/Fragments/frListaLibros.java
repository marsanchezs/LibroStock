package com.example.librostock.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.librostock.Adaptadores.AdaptadorFilaLibro;
import com.example.librostock.Adaptadores.AdaptadorRecycler;
import com.example.librostock.BBDD.LibroStockConstantes;
import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.BBDD.LibroStockOpenHelper;
import com.example.librostock.DetalleLibro;
import com.example.librostock.InformacionApp;
import com.example.librostock.Objetos.Libro;
import com.example.librostock.R;

import java.util.ArrayList;
import java.util.Objects;

public class frListaLibros extends Fragment implements AdaptadorRecycler.clickLibro{

    private static String NOMBRE_BBDD = LibroStockConstantes.NOMBRE_BBDD;
    private static int VERSION_BBDD = LibroStockConstantes.VERSION_BBDD;
    private ListView lvLibros;
    private RecyclerView rvListaLibros;
    private LinearLayout ll1, ll2;
    private Toast miMensaje;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_fr_lista_libros, container, false);

        ll1 = (LinearLayout) vista.findViewById(R.id.llvFRLL1);
        ll2 = (LinearLayout) vista.findViewById(R.id.llvFRLL2);
        rvListaLibros = (RecyclerView) vista.findViewById(R.id.rvListaLibrosFRLL);
        LinearLayoutManager admDisenoLineal = new LinearLayoutManager(getActivity());
        rvListaLibros.setLayoutManager(admDisenoLineal);
        cargarRecyclerView();

        //lvLibros = (ListView) vista.findViewById(R.id.lvListaLibrosFRLL);
        //cargarListView();

        // Inflate the layout for this fragment
        return vista;
    }

    //MÉTODOS
    private void capturarFila(final ArrayList<Libro> listaLibros){
        lvLibros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Context contexto = getActivity();
                String titulo, autor, genero;
                titulo = listaLibros.get(position).getTitulo();
                autor = listaLibros.get(position).getAutor();
                genero = listaLibros.get(position).getGenero();
                String mensaje = "TÍTULO: "+titulo+" AUTOR: "+autor+" GÉNERO: "+genero;
                Toast.makeText(contexto, mensaje, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void cargarRecyclerView(){
        ArrayList<Libro> lista = traerLibros(getActivity());

        if(lista.size() == 0){
            ll1.setVisibility(View.VISIBLE);
            ll2.setVisibility(View.GONE);
        }else{
            ll1.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
            System.out.println("LISTA DE LIBROS RV: "+lista);
            AdaptadorRecycler adaptador = new AdaptadorRecycler(lista, this);
            rvListaLibros.setAdapter(adaptador);
        }
    }

    /*public void cargarListView(){
        Context contexto = getActivity();
        ArrayList<Libro> lista = traerLibros(contexto);
        capturarFila(lista);
        System.out.println("LISTA DE LIBROS: "+lista);
        AdaptadorFilaLibro miAdaptador = new AdaptadorFilaLibro(contexto, lista);
        lvLibros.setAdapter(miAdaptador);
    }*/

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


    @Override
    public void clickListaLibro(int clickedLibro) {
        ArrayList<Libro> lista = traerLibros(getActivity());
        if(miMensaje != null){
            miMensaje.cancel();
        }
        String titulo = lista.get(clickedLibro).getTitulo();
        String autor = lista.get(clickedLibro).getAutor();
        String genero = lista.get(clickedLibro).getGenero();

        irDetalleLibro(titulo, autor, genero);

        /*String mensaje = titulo.toUpperCase()+" DE "+autor.toUpperCase()+" SELECCIONADO";
        miMensaje = Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG);
        miMensaje.show();*/
    }

    @Override
    public void clickLargoListaLibro(int clickedLibro) {
        ArrayList<Libro> lista = traerLibros(getActivity());
        if(miMensaje != null){
            miMensaje.cancel();
        }
        String titulo = lista.get(clickedLibro).getTitulo();
        String autor = lista.get(clickedLibro).getAutor();
        String genero = lista.get(clickedLibro).getGenero();

        String mensaje = titulo.toUpperCase()+" DE "+autor.toUpperCase()+" SELECCIONADOLC";
        miMensaje = Toast.makeText(getActivity(), mensaje, Toast.LENGTH_LONG);
        miMensaje.show();
    }

    private void irDetalleLibro(String titulo, String autor, String genero){
        Bundle datosLibro = new Bundle();
        datosLibro.putString("Titulo", titulo);
        datosLibro.putString("Autor", autor);
        datosLibro.putString("Genero", genero);
        datosLibro.putString("Actividad", "frListaLibros");
        Context contexto = getActivity();
        Intent intento = new Intent(contexto, DetalleLibro.class);
        intento.putExtras(datosLibro);
        startActivity(intento);
    }
}