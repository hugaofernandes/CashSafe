package com.cashsafe.cashsafe.atividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cashsafe.cashsafe.DAO.CategoriaDespesaDAO;
import com.cashsafe.cashsafe.DAO.DespesaDAO;
import com.cashsafe.cashsafe.DAO.ReceitaDAO;
import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.Util.AdapterListView;
import com.cashsafe.cashsafe.modelo.Categoria;
import com.cashsafe.cashsafe.modelo.CategoriaDespesa;
import com.cashsafe.cashsafe.modelo.Despesa;
import com.cashsafe.cashsafe.modelo.Movimentacao;
import com.cashsafe.cashsafe.modelo.Receita;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

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

        graficoDespesas = (PieChart) findViewById(R.id.categoriaschart);
        graficoDespesas.setDrawHoleEnabled(false);
        graficoDespesas.setDescription("");
        graficoDespesas.setRotationEnabled(false);

        graficoReceitas = (PieChart) findViewById(R.id.receitaschart);
        graficoReceitas.setDrawHoleEnabled(false);
        graficoReceitas.setDescription("");
        graficoReceitas.setRotationEnabled(false);



        Legend l = graficoDespesas.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        l = graficoReceitas.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        this.setValoresRecEdes();
        this.setValoresGraficoDespesa();
        this.setValoresGraficoReceita();

    }


    @Override
    protected void onResume() {
        super.onResume();
        this.setValoresRecEdes();
        this.setValoresGraficoDespesa();
        this.setValoresGraficoReceita();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.abrir_categorias) {
            Intent intent = new Intent(this, CategoriasActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setValoresRecEdes(){
        ReceitaDAO daoReceitas = new ReceitaDAO(this.getBaseContext());
        DespesaDAO daoDespesas = new DespesaDAO(this.getBaseContext());

        Calendar hoje = Calendar.getInstance();

        Double valorTotalReceitas = daoReceitas.getSomaReceitaPorMes(hoje);
        TextView totalReceitas = (TextView)findViewById(R.id.valor_receita_principal);
        totalReceitas.setText("R$" + String.format("%.2f", valorTotalReceitas));


        Double valorTotalDespesas = daoDespesas.getSomaDespesasPorMes(hoje);
        TextView totalDespesas = (TextView)findViewById(R.id.valor_despesas_principal);
        totalDespesas.setText("R$" + String.format("%.2f", valorTotalDespesas));


        Double valorTotalBalanco = valorTotalReceitas-valorTotalDespesas;
        TextView totalBalanco = (TextView)findViewById(R.id.valor_saldo_principal);
        int cor = (valorTotalBalanco < 0) ? getResources().getColor(R.color.POMEGRANATE) : getResources().getColor(R.color.PICTONBLUE);
        totalBalanco.setTextColor(cor);
        totalBalanco.setText("R$" + String.format("%.2f", valorTotalBalanco));

        daoDespesas.fecharBanco();
        daoReceitas.fecharBanco();
    }

    public void setValoresGraficoDespesa(){
        DespesaDAO daoDespesas = new DespesaDAO(this.getBaseContext());

        HashMap<String, Double> resultado = daoDespesas.getSomaValoresPorCategoria();


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        int i = 0;
        for (Map.Entry<String, Double> entry : resultado.entrySet())
        {
            yVals1.add(new Entry(Float.parseFloat(entry.getValue().toString()), i));
            xVals.add(entry.getKey());
            i++;
        }


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

        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        graficoDespesas.setData(data);

        // undo all highlights
        graficoDespesas.highlightValues(null);

        // update pie chart
        graficoDespesas.invalidate();
        graficoDespesas.getLegend().setEnabled(false);

        daoDespesas.fecharBanco();

    }

    public void setValoresGraficoReceita(){
        ReceitaDAO receitaDAO = new ReceitaDAO(this.getBaseContext());

        HashMap<String, Double> resultado = receitaDAO.getSomaValoresPorCategoria();


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> xVals = new ArrayList<String>();
        int i = 0;
        for (Map.Entry<String, Double> entry : resultado.entrySet())
        {
            yVals1.add(new Entry(Float.parseFloat(entry.getValue().toString()), i));
            xVals.add(entry.getKey());
            i++;
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "Receitas");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

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

        PieData data = new PieData(xVals, dataSet);
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        graficoReceitas.setData(data);

        graficoReceitas.highlightValues(null);
        graficoReceitas.invalidate();
        graficoReceitas.getLegend().setEnabled(false);

        receitaDAO.fecharBanco();

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



}
