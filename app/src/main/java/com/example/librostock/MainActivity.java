package com.example.librostock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.example.librostock.Fragments.AdaptadorFragments;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    Toolbar barraHerramientas;
    TabLayout tabLayout;
    ViewPager visor;
    TabItem tabListaLibros, tabAgregarLibro, tabAgregarAutor;
    AdaptadorFragments miAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        barraHerramientas = (Toolbar) findViewById(R.id.miBarraHerramientas);
        setSupportActionBar(barraHerramientas);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        tabLayout = findViewById(R.id.tabLayout);
        visor = findViewById(R.id.visor);
        tabListaLibros = findViewById(R.id.tabListaLibros);
        tabAgregarLibro = findViewById(R.id.tabAgregarLibro);
        tabAgregarAutor = findViewById(R.id.tabAgregarAutor);

        miAdaptador = new AdaptadorFragments(getSupportFragmentManager(), tabLayout.getTabCount());
        visor.setAdapter(miAdaptador);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(visor));
        visor.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    //MÉTODOS
    public void irBuscarLibro(View vista){
        Context contexto = getApplicationContext();
        Intent intento = new Intent(contexto, BuscarLibro.class);
        startActivity(intento);
    }

    public void irInformacionApp(View vista){
        Context contexto = getApplicationContext();
        Intent intento = new Intent(contexto, InformacionApp.class);
        startActivity(intento);
    }

    public void salirApp(View vista){
        finish();
    }

    @Override public boolean onCreateOptionsMenu(Menu miMenu){
        getMenuInflater().inflate(R.menu.mi_menu, miMenu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem opcionMenu){
        int id = opcionMenu.getItemId();
        switch(opcionMenu.getItemId()){
            case R.id.menuBuscar:
                irBuscarLibro(null);
                return true;
            case R.id.menuInformacion:
                irInformacionApp(null);
                return true;
            case R.id.menuSalir:
                salirApp(null);
                return true;
            default:
                return super.onOptionsItemSelected(opcionMenu);
        }
    }
}