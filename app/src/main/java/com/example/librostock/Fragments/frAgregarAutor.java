package com.example.librostock.Fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

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
import android.widget.Toast;

import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.Interfaces.InterfazFragment;
import com.example.librostock.MainActivity;
import com.example.librostock.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class frAgregarAutor extends Fragment {

    private AutoCompleteTextView edtPaisAutor;
    private Button btnAgregarAutor;
    private EditText edtNombreAutor, edtApellidoAutor;
    private ImageView imgLimpiarNombre, imgLimpiarApellido, imgLimpiarPais;
    private LibroStockDelegar delegar = new LibroStockDelegar();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_fr_agregar_autor, container, false);

        edtNombreAutor = (EditText) vista.findViewById(R.id.edtNombreAutorFRAA);
        edtApellidoAutor = (EditText) vista.findViewById(R.id.edtApellidoAutorFRAA);
        edtPaisAutor = (AutoCompleteTextView) vista.findViewById(R.id.edtPaisAutorFRAA);
        imgLimpiarNombre = (ImageView) vista.findViewById(R.id.imgLimpiarNombreFRAA);
        imgLimpiarApellido = (ImageView) vista.findViewById(R.id.imgLimpiarApellidoFRAA);
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
        mostrarBotonLimpiarApellido();
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

    private void mostrarBotonLimpiarApellido(){
        imgLimpiarApellido.setOnClickListener( new View.OnClickListener() {
            public void onClick(View view){
                limpiarApellido();
            }
        });

        edtApellidoAutor.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(edtApellidoAutor.getText().toString().length() == 0){
                    imgLimpiarApellido.setVisibility(View.GONE);
                }else{
                    imgLimpiarApellido.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void limpiarApellido(){
        edtApellidoAutor.setText("");
        edtApellidoAutor.requestFocus();
        imgLimpiarApellido.setVisibility(View.GONE);
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
        String apellido = edtApellidoAutor.getText().toString();
        String pais = edtPaisAutor.getText().toString();
        String mensaje = "SIN MENSAJE";
        if((nombre.isEmpty()) && (apellido.isEmpty()) && (pais.isEmpty())){
            mensaje = "DEBE INGRESAR TODOS LOS DATOS";
            Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
        }else if(nombre.isEmpty()){
            mensaje = "INGRESAR NOMBRE";
            edtNombreAutor.setError(mensaje);
        }else if(apellido.isEmpty()){
            mensaje = "INGRESAR APELLIDO";
            edtApellidoAutor.setError(mensaje);
        }else if(pais.isEmpty()){
            mensaje = "INGRESAR PAÍS";
            edtPaisAutor.setError(mensaje);
        }else{
            agregarAutor();
        }

    }

    private void agregarAutor(){
        Context contexto = getActivity();
        String nombre = edtNombreAutor.getText().toString();
        String apellido = edtApellidoAutor.getText().toString();
        String pais = edtPaisAutor.getText().toString();
        String mensaje = "NOMBRE: "+nombre+" APELLIDO: "+apellido+" PAÍS: "+pais;
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
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

}