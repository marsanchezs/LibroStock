package com.example.librostock;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.Objects;

public class Estadisticas extends AppCompatActivity {

    private Context contexto;
    private TextView txtTitulo;
    private Toolbar toolbar;
    private BarChart graficoBarra;
    private PieChart graficoCircular;
    private String[] meses = new String[]{"ENE", "FEB", "MAR", "ABR", "MAY", "JUN"};
    private int[] ventas = new int[]{15, 20, 10, 40, 30, 50};
    private int[] colores = new int[]{Color.GREEN, Color.BLUE, Color.YELLOW, Color.RED, Color.MAGENTA, Color.CYAN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        contexto = getApplicationContext();
        txtTitulo = (TextView) findViewById(R.id.barraHerramientasTitulo);
        toolbar = (Toolbar) findViewById(R.id.miBarraHerramientasE);
        cargarToolbar();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volver(null);
            }
        });

        graficoBarra = (BarChart) findViewById(R.id.graficoBarra);
        graficoCircular = (PieChart) findViewById(R.id.graficoCircular);
        crearGrafico();

    }

    //MÉTODOS
    private Chart getSameChart(Chart grafico, String descripcion,
                               int colorTexto, int colorFondo, int tiempo){
        grafico.getDescription().setText(descripcion);
        grafico.getDescription().setTextSize(20);
        grafico.getDescription().setTextColor(colorTexto);
        grafico.setBackgroundColor(colorFondo);
        grafico.animateY(tiempo);
        leyenda(grafico);
        return  grafico;
    }

    private void leyenda(Chart grafico){
        Legend leyenda = grafico.getLegend();
        leyenda.setForm(Legend.LegendForm.CIRCLE);
        leyenda.setTextColor(Color.YELLOW);
        leyenda.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> datos = new ArrayList<>();
        for(int i = 0; i < meses.length; i++){
            LegendEntry entrada = new LegendEntry();
            entrada.formColor = colores[i];
            entrada.label = meses[i];
            datos.add(entrada);
        }
        leyenda.setCustom(datos);
    }

    private ArrayList<BarEntry> getBarEntry(){
        ArrayList<BarEntry> datos = new ArrayList<>();
        for(int i = 0; i < ventas.length; i++){
            datos.add(new BarEntry(i, ventas[i]));
        }
        return datos;
    }

    private ArrayList<PieEntry> getPieEntry(){
        ArrayList<PieEntry> datos = new ArrayList<>();
        for(int i = 0; i < ventas.length; i++){
            datos.add(new PieEntry(ventas[i]));
        }
        return datos;
    }

    private void ejeX(XAxis eje){
        eje.setGranularityEnabled(true);
        eje.setPosition(XAxis.XAxisPosition.BOTTOM);
        eje.setValueFormatter(new IndexAxisValueFormatter(meses));
        eje.setEnabled(false);
    }

    private void ejeYIzquierdo(YAxis eje){
        eje.setSpaceTop(30);
        eje.setAxisMinimum(0);
    }

    private void ejeYDerecho(YAxis eje){
        eje.setEnabled(false);
    }

    public void crearGrafico(){
        graficoBarra = (BarChart) getSameChart(graficoBarra, "Series", Color.WHITE, R.color.colorFondo, 3000);
        graficoBarra.setDrawGridBackground(true);
        graficoBarra.setDrawBarShadow(true);
        graficoBarra.setData(getBarData());
        graficoBarra.invalidate();
        ejeX(graficoBarra.getXAxis());
        ejeYDerecho(graficoBarra.getAxisRight());
        ejeYIzquierdo(graficoBarra.getAxisLeft());

        graficoCircular = (PieChart) getSameChart(graficoCircular, "Ventas", Color.WHITE, R.color.colorFondo, 5000);
        graficoCircular.setHoleRadius(10);
        graficoCircular.setTransparentCircleRadius(12);
        graficoCircular.setData(getPieData());
        graficoCircular.invalidate();
        graficoCircular.setDrawHoleEnabled(false);
    }

    private DataSet getData(DataSet datos){
        datos.setColors(colores);
        datos.setValueTextColor(Color.WHITE);
        datos.setValueTextSize(10);
        return datos;
    }

    private BarData getBarData(){
        BarDataSet datosBarra = (BarDataSet) getData(new BarDataSet(getBarEntry(), ""));
        datosBarra.setBarShadowColor(Color.GRAY);
        BarData barData = new BarData(datosBarra);
        barData.setBarWidth(0.45f);
        return barData;
    }

    private PieData getPieData(){
        PieDataSet datosCirculo = (PieDataSet) getData(new PieDataSet(getPieEntry(), ""));
        datosCirculo.setSliceSpace(2);
        datosCirculo.setValueFormatter(new PercentFormatter());
        return new PieData(datosCirculo);
    }

    private void cargarToolbar(){
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        txtTitulo.setText(R.string.estadisticas);
        ((TextView) txtTitulo).setTextSize(30);
    }

    public void volver(View vista){
        Intent intento = new Intent(contexto, MainActivity.class);
        startActivity(intento);
    }
}