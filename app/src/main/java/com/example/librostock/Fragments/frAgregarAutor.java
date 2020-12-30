package com.example.librostock.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.MainActivity;
import com.example.librostock.Objetos.Autor;
import com.example.librostock.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class frAgregarAutor extends Fragment {

    private AutoCompleteTextView edtPaisAutor;
    private Button btnAgregarAutor;
    private EditText edtNombreAutor;
    private RadioButton rbMasculino, rbFemenino;
    private ImageView imgLimpiarNombre, imgLimpiarPais;
    private LibroStockDelegar delegar = new LibroStockDelegar();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_fr_agregar_autor, container, false);

        edtNombreAutor = (EditText) vista.findViewById(R.id.edtNombreAutorFRAA);
        rbMasculino = (RadioButton) vista.findViewById(R.id.rbMasculinoFRAL);
        rbFemenino = (RadioButton) vista.findViewById(R.id.rbFemeninoFRAL);
        edtPaisAutor = (AutoCompleteTextView) vista.findViewById(R.id.edtPaisAutorFRAA);
        imgLimpiarNombre = (ImageView) vista.findViewById(R.id.imgLimpiarNombreFRAA);
        imgLimpiarPais = (ImageView) vista.findViewById(R.id.imgLimpiarPaisFRAA);
        btnAgregarAutor = (Button) vista.findViewById(R.id.btnAgregarFRAA);

        edtNombreAutor.requestFocus();

        btnAgregarAutor.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                validar();
            }
        });

        traerPaises();
        mostrarBotonesLimpiar();

        // Inflate the layout for this fragment
        return vista;
    }

    //MÉTODOS
    private void mostrarBotonesLimpiar(){
        mostrarBotonLimpiarNombre();
        mostrarBotonLimpiarPais();
    }

    private void mostrarBotonLimpiarPais(){
        imgLimpiarPais.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarPais();
            }
        });

        edtPaisAutor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imgLimpiarPais.setVisibility(View.VISIBLE);
            }
        });

        edtPaisAutor.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtPaisAutor.getText().toString().length() == 0){
                    imgLimpiarPais.setVisibility(View.GONE);
                }else{
                    imgLimpiarPais.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void limpiarPais(){
        edtPaisAutor.setText("");
        edtPaisAutor.requestFocus();
        imgLimpiarPais.setVisibility(View.GONE);
    }

    private void mostrarBotonLimpiarNombre(){
        imgLimpiarNombre.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarNombre();
            }
        });

        edtNombreAutor.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtNombreAutor.getText().toString().length() == 0){
                    imgLimpiarNombre.setVisibility(View.GONE);
                }else{
                    imgLimpiarNombre.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void limpiarNombre(){
        edtNombreAutor.setText("");
        edtNombreAutor.requestFocus();
        imgLimpiarNombre.setVisibility(View.GONE);
    }

    private void validar(){
        Context contexto = getActivity();
        String nombre = edtNombreAutor.getText().toString();
        String pais = edtPaisAutor.getText().toString();
        String mensaje = "SIN MENSAJE";
        if((nombre.isEmpty()) && (pais.isEmpty())){
            mensaje = "DEBE INGRESAR TODOS LOS DATOS";
            Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
        }else if(nombre.isEmpty()){
            mensaje = "INGRESAR NOMBRE";
            edtNombreAutor.setError(mensaje);
        }else if(pais.isEmpty()){
            mensaje = "INGRESAR PAÍS";
            edtPaisAutor.setError(mensaje);
        }else{
            agregarAutor();
        }

    }

    private void agregarAutor(){
        String nombre = edtNombreAutor.getText().toString();
        String pais = edtPaisAutor.getText().toString();
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setPais(pais);
        if(rbMasculino.isChecked()){
            autor.setSexo("Masculino");
        }else if(rbFemenino.isChecked()){
            autor.setSexo("Femenino");
        }
        validarAutor(autor);
    }

    private void validarAutor(Autor autor){
        Context contexto = getActivity();
        String mensaje = "";
        String respuestaValidarAutor = delegar.validarAutor(contexto, autor);
        if(respuestaValidarAutor.equals("AUTOR EXISTE")){
            if(autor.getSexo().equals("Masculino")){
                mensaje = autor.getNombre().toUpperCase()+" YA SE ENCUENTRA AGREGADO";
            }else if(autor.getSexo().equals("Femenino")){
                mensaje = autor.getNombre().toUpperCase()+" YA SE ENCUENTRA AGREGADA";
            }
            Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
        }else{
            String respuestaAgregarAutor = delegar.agregarAutor(contexto, autor);
            if(respuestaAgregarAutor.equals("OK")){
                if(autor.getSexo().equals("Masculino")){
                    mensaje = autor.getNombre().toUpperCase()+" HA SIDO AGREGADO";
                }else if(autor.getSexo().equals("Femenino")){
                    mensaje = autor.getNombre().toUpperCase()+" HA SIDO AGREGADA";
                }
                Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
                limpiar();
            }else{
                Toast.makeText(contexto, respuestaAgregarAutor, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void traerPaises() {
        Context contexto = getActivity();
        ArrayList<String> paises = delegar.traerPaises(contexto);
        System.out.println("LISTA PAÍSES: " + paises.toString());
        String[] arrayPaises = new String[paises.size()];
        arrayPaises = paises.toArray(arrayPaises);
        assert contexto != null;
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(contexto, R.layout.lista_desplegable, R.id.txt, arrayPaises);
        edtPaisAutor.setAdapter(adaptador);

        edtPaisAutor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    private void limpiar(){
        edtNombreAutor.setText("");
        edtPaisAutor.setText("");
        edtNombreAutor.requestFocus();
    }

}