package com.cashsafe.cashsafe.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cashsafe.cashsafe.DAO.DespesaDAO;
import com.cashsafe.cashsafe.DAO.ReceitaDAO;
import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.Util.AdapterListView;
import com.cashsafe.cashsafe.modelo.Despesa;
import com.cashsafe.cashsafe.modelo.Movimentacao;
import com.cashsafe.cashsafe.modelo.Receita;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BalancoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balanco);

        getSupportActionBar().setTitle("Balanço");
        ReceitaDAO daoReceitas = new ReceitaDAO(this.getBaseContext());
        ArrayList<Movimentacao> movimentacoesReceitas = (ArrayList<Movimentacao>) daoReceitas.getTodasReceitasAsMovimentacoes();

        ListView listReceitas= (ListView)findViewById( R.id.lista_receitas);
        AdapterListView adapter = new AdapterListView(this.getBaseContext(), movimentacoesReceitas);
        listReceitas.setAdapter(adapter);

        Double valorTotalReceitas = daoReceitas.getSomaValores();
        TextView totalReceitas = (TextView)findViewById(R.id.total_receitas);
        totalReceitas.setText("R$"+String.format("%.2f", valorTotalReceitas));

        daoReceitas.closePool();

        DespesaDAO daoDespesas = new DespesaDAO(this.getBaseContext());
        ArrayList<Movimentacao> movimentacoesDespesas = (ArrayList<Movimentacao>) daoDespesas.getTodasDespesasAsMovimentacoes();

        ListView listaDespesas= (ListView)findViewById( R.id.lista_despesas);
        adapter = new AdapterListView(this.getBaseContext(), movimentacoesDespesas);
        listaDespesas.setAdapter(adapter);

        Double valorTotalDespesas = daoDespesas.getSomaValores();
        TextView totalDespesas = (TextView)findViewById(R.id.total_despesas);
        totalDespesas.setText("R$"+String.format("%.2f", valorTotalDespesas));

        daoDespesas.closePool();

        Double valorTotalBalanco = valorTotalReceitas-valorTotalDespesas;
        TextView totalBalanco = (TextView)findViewById(R.id.valor_balanco);
        int cor = (valorTotalBalanco < 0) ? getResources().getColor(R.color.POMEGRANATE) : getResources().getColor(R.color.PICTONBLUE);
        totalBalanco.setTextColor(cor);
        totalBalanco.setText("R$"+String.format("%.2f", valorTotalBalanco));
    }
}