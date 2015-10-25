package com.cashsafe.cashsafe.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

public class BalancoActivity extends AppCompatActivity {
    private ListView listaReceitas;
    private TextView totalReceitas;
    private ListView listaDespesas;
    private TextView totalDespesas;
    private TextView totalBalanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balanco);
        listaReceitas = (ListView)findViewById(R.id.lista_receitas);
        totalReceitas = (TextView)findViewById(R.id.total_receitas);
        listaDespesas= (ListView)findViewById(R.id.lista_despesas);
        totalDespesas = (TextView)findViewById(R.id.total_despesas);
        totalBalanco = (TextView)findViewById(R.id.valor_balanco);
        listaReceitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                System.out.println("editar receita");
                Receita receitaSelecionada = (Receita) (listaReceitas.getItemAtPosition(myItemInt));
                editarReceita(receitaSelecionada);
            }
        });
        listaDespesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                Despesa despesaSelecionada= (Despesa) (listaDespesas.getItemAtPosition(myItemInt));
                editarDespesa(despesaSelecionada);
            }
        });
        getSupportActionBar().setTitle("Balanço");
        this.atualizarData();

    }
    @Override
    protected void onResume() {
        super.onResume();
        this.atualizarData();
    }
    public void atualizarData(){
        ReceitaDAO daoReceitas = new ReceitaDAO(this.getBaseContext());

        ArrayList<Movimentacao> movimentacoesReceitas = (ArrayList<Movimentacao>) daoReceitas.getTodasReceitasAsMovimentacoes();
        listaReceitas.setAdapter(new AdapterListView(this.getBaseContext(), movimentacoesReceitas));

        Double valorTotalReceitas = daoReceitas.getSomaValores();
        totalReceitas.setText("R$"+String.format("%.2f", valorTotalReceitas));

        daoReceitas.fecharBanco();

        DespesaDAO daoDespesas = new DespesaDAO(this.getBaseContext());
        ArrayList<Movimentacao> movimentacoesDespesas = (ArrayList<Movimentacao>) daoDespesas.getTodasDespesasAsMovimentacoes();

        listaDespesas.setAdapter(new AdapterListView(this.getBaseContext(), movimentacoesDespesas));

        Double valorTotalDespesas = daoDespesas.getSomaValores();
        totalDespesas.setText("R$"+String.format("%.2f", valorTotalDespesas));

        daoDespesas.fecharBanco();

        Double valorTotalBalanco = valorTotalReceitas-valorTotalDespesas;
        int cor = (valorTotalBalanco < 0) ? getResources().getColor(R.color.POMEGRANATE) : getResources().getColor(R.color.PICTONBLUE);
        totalBalanco.setTextColor(cor);
        totalBalanco.setText("R$" + String.format("%.2f", valorTotalBalanco));
    }

    public void editarReceita(Receita receita){
        Intent intent = new Intent(this, EditarReceitaActivity.class);
        intent.putExtra("receita",receita);
        startActivity(intent);
    }

    public void editarDespesa(Despesa despesa){
        Intent intent = new Intent(this, EditarDespesaActivity.class);
        intent.putExtra("despesa",despesa);
        startActivity(intent);
    }
}