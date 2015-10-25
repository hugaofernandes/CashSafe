package com.cashsafe.cashsafe.atividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cashsafe.cashsafe.DAO.CategoriaDespesaDAO;
import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.modelo.Categoria;
import com.cashsafe.cashsafe.modelo.CategoriaDespesa;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class PrincipalActivity extends AppCompatActivity {


    private PieChart graficoDespesas;
    private PieChart graficoReceitas;
    float[] yDataDespesas = { 15, 10 };
    String[] xDataDespesas = { "Taxi", "Aluguel"};
    float[] yDataReceitas = { 20, 2 };
    String[] xDataReceitas = { "Salario", "Presente"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Intent intent = getIntent();

        // configure pie chart
        graficoDespesas = (PieChart) findViewById(R.id.categoriaschart);
        graficoDespesas.setDrawHoleEnabled(false);
        graficoDespesas.setDescription("");
        graficoDespesas.setRotationEnabled(false);

        graficoReceitas = (PieChart) findViewById(R.id.receitaschart);
        graficoReceitas.setDrawHoleEnabled(false);
        graficoReceitas.setDescription("");
        graficoReceitas.setRotationEnabled(false);

        adicionarDadosDespesas();
        adicionarDadosReceitas();
        Legend l = graficoDespesas.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        l = graficoReceitas.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

    }

    public void balancoMensal(View view) {
        Intent intent = new Intent(this, BalancoActivity.class);
        startActivity(intent);
    }

    public void receitasPorCategoria(View view) {
        Intent intent = new Intent(this, ReceitaCategoriaActivity.class);
        startActivity(intent);
    }

    public void despesasPorCategoria(View view) {
        Intent intent = new Intent(this, DespesasCategoriaActivity.class);
        startActivity(intent);
    }

    public void cadastroReceita(View view) {
        Intent intent = new Intent(this, ReceitaActivity.class);
        startActivity(intent);
    }

    public void cadastroDespesa(View view) {
        Intent intent = new Intent(this, DespesaActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void adicionarDadosDespesas() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yDataDespesas.length; i++)
            yVals1.add(new Entry(yDataDespesas[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xDataDespesas.length; i++)
            xVals.add(xDataDespesas[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Despesas");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        graficoDespesas.setData(data);

        // undo all highlights
        graficoDespesas.highlightValues(null);

        // update pie chart
        graficoDespesas.invalidate();
        graficoDespesas.getLegend().setEnabled(false);
    }

    private void adicionarDadosReceitas() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yDataReceitas.length; i++)
            yVals1.add(new Entry(yDataReceitas[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xDataReceitas.length; i++)
            xVals.add(xDataReceitas[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Receitas");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        graficoReceitas.setData(data);

        // undo all highlights
        graficoReceitas.highlightValues(null);

        // update pie chart
        graficoReceitas.invalidate();
        graficoReceitas.getLegend().setEnabled(false);
    }
}
