package com.example.librostock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.librostock.Adaptadores.AdaptadorRecyclerAutores;
import com.example.librostock.BBDD.LibroStockDelegar;
import com.example.librostock.Fragments.frAgregarAutor;
import com.example.librostock.Objetos.Autor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class DetalleAutor extends AppCompatActivity {

    private Bundle datosAutor;
    private Context contexto;
    private String nombre, pais, sexo;
    private ImageView imgAutor;
    private TextView txtTitulo, txtNombre, txtPais, txtSexo;
    private Toolbar toolbar;
    private LibroStockDelegar delegar = new LibroStockDelegar();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_autor);

        recibirDatos();
        cargarMedidas();
        contexto = getApplicationContext();
        toolbar = (Toolbar) findViewById(R.id.miBarraHerramientasDA);
        txtTitulo = (TextView) findViewById(R.id.barraHerramientasTitulo);
        imgAutor = (ImageView) findViewById(R.id.imgAutorDA);
        txtNombre = (TextView) findViewById(R.id.txtNombreDA);
        txtPais = (TextView) findViewById(R.id.txtPaisDA);
        txtSexo = (TextView) findViewById(R.id.txtSexoDA);
        cargarToolbar();
        cargarDatos();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //MÉTODOS
    private void cargarToolbar(){
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        txtTitulo.setText(R.string.tituloDetalleAutor);
        txtTitulo.setTextSize(30);
        ((TextView) txtTitulo).setTextSize(30);
    }

    private void recibirDatos(){
        datosAutor = this.getIntent().getExtras();
        assert datosAutor != null;
        nombre = datosAutor.getString("Autor");
        pais = datosAutor.getString("Pais");
        sexo = datosAutor.getString("Sexo");
        System.out.println("NOMBRE: "+nombre+" PAÍS: "+pais+" SEXO: "+sexo);
    }

    private void cargarDatos(){
        switch(nombre){
            case "anónimo":
                imgAutor.setImageResource(R.drawable.icono_anonimo);
                break;
            case "varios autores":
                imgAutor.setImageResource(R.drawable.icono_autorg);
                break;
        }

        switch(sexo){
            case "Masculino":
                imgAutor.setImageResource(R.drawable.icono_autorh);
                break;
            case "Femenino":
                imgAutor.setImageResource(R.drawable.icono_autorm);
                break;
        }

        txtNombre.setText(nombre);
        txtPais.setText(pais);
        txtSexo.setText(sexo);
    }

    public void cargarMedidas(){
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.95), (int)(alto * 0.70));
    }

    private void editarAutor(String nombre, String pais, String sexo){
        String mensaje = "AUTOR: "+nombre+" PAíS: "+pais+" SEXO: "+sexo;
        Toast.makeText(contexto, mensaje, Toast.LENGTH_SHORT).show();
    }

    private void eliminarAutor(final String nombre, final String pais, final String sexo){
        final AlertDialog dialogoEliminar;
        final AlertDialog.Builder constructorDialogo = new AlertDialog.Builder(DetalleAutor.this);
        LayoutInflater inflador = getLayoutInflater();
        View vistaDialogo = inflador.inflate(R.layout.dialogo_eliminar,null);
        constructorDialogo.setView(vistaDialogo);
        dialogoEliminar = constructorDialogo.create();
        TextView txvMensaje = (TextView) vistaDialogo.findViewById(R.id.txtMensajeDE);
        ImageButton btnConfirmar = (ImageButton) vistaDialogo.findViewById(R.id.btnConfirmarDE);
        ImageButton btnCancelar = (ImageButton) vistaDialogo.findViewById(R.id.btnCancelarDE);
        final String mensaje = "¿ESTÁ SEGURO DE ELIMINAR A "+nombre.toUpperCase()+"?";
        txvMensaje.setText(mensaje);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //String mensaje2 = "AUTOR: "+nombre+" PAíS: "+pais+" SEXO: "+sexo;
                //Toast.makeText(contexto, mensaje2, Toast.LENGTH_SHORT).show();
                String msg = "";
                String respuestaBorrarAutor = delegar.borrarAutor(contexto, nombre, pais, sexo);
                if(respuestaBorrarAutor.equals("OK")){
                    msg = nombre.toUpperCase()+" HA SIDO ELIMINADO";
                    Toast.makeText(contexto, msg, Toast.LENGTH_SHORT).show();
                    volver(null);
                    finish();
                    dialogoEliminar.dismiss();
                }else{
                    msg = "NO SE PUDO ELIMINAR A "+nombre.toUpperCase();
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

    @Override public boolean onCreateOptionsMenu(Menu miMenu){
        getMenuInflater().inflate(R.menu.mi_menu2, miMenu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem opcionMenu){
        int id = opcionMenu.getItemId();
        switch(opcionMenu.getItemId()){
            case R.id.menuEditar:
                editarAutor(nombre, pais, sexo);
                return true;
            case R.id.menuEliminar:
                eliminarAutor(nombre, pais, sexo);
                return true;
            default:
                return super.onOptionsItemSelected(opcionMenu);
        }
    }

    public void volver(View vista){
        Intent intento = new Intent(contexto, MainActivity.class);
        startActivity(intento);
    }

    public void actualizar(){
        frAgregarAutor fragment = new frAgregarAutor();
        ((frAgregarAutor) fragment).setUserVisibleHint(true);
    }

}