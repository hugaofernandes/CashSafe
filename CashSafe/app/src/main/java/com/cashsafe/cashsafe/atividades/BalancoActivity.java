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
import java.util.Calendar;

public class BalancoActivity extends AppCompatActivity {
    private ListView listaReceitas;
    private TextView totalReceitas;
    private ListView listaDespesas;
    private TextView totalDespesas;
    private TextView totalBalanco;
    private TextView  dataVisualizacaoText;
    private Calendar dataVisualizacao;
    ArrayList<Movimentacao> todasAsReceitas;
    ArrayList<Movimentacao> todasAsDespesas;
    private final String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balanco);
        listaReceitas = (ListView)findViewById(R.id.lista_receitas);
        totalReceitas = (TextView)findViewById(R.id.total_receitas);
        listaDespesas= (ListView)findViewById(R.id.lista_despesas);
        totalDespesas = (TextView)findViewById(R.id.total_despesas);
        totalBalanco = (TextView)findViewById(R.id.valor_balanco);
        dataVisualizacaoText = (TextView)findViewById(R.id.data_visualizacao);
        listaReceitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                System.out.println("editar receita");
                Receita receitaSelecionada = (Receita) (listaReceitas.getItemAtPosition(myItemInt));
                editarReceita(receitaSelecionada);
            }
        });
        listaDespesas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                Despesa despesaSelecionada = (Despesa) (listaDespesas.getItemAtPosition(myItemInt));
                editarDespesa(despesaSelecionada);
            }
        });
        getSupportActionBar().setTitle("Balanço");
        this.dataVisualizacao = Calendar.getInstance();
        this.carregarDados();

    }
    @Override
    protected void onResume() {
        super.onResume();
        this.carregarDados();
    }

    public void proximo(View view){
        this.dataVisualizacao.add(Calendar.MONTH, 1);
        this.atualizarDadosTela();
    }
    public void anterior(View view){
        this.dataVisualizacao.add(Calendar.MONTH, -1);
        this.atualizarDadosTela();
    }
    public void carregarDados(){
        ReceitaDAO daoReceitas = new ReceitaDAO(this.getBaseContext());
        this.todasAsReceitas = (ArrayList<Movimentacao>) daoReceitas.getTodasReceitasAsMovimentacoes();
        daoReceitas.fecharBanco();

        DespesaDAO daoDespesas = new DespesaDAO(this.getBaseContext());
        this.todasAsDespesas = (ArrayList<Movimentacao>) daoDespesas.getTodasDespesasAsMovimentacoes();
        daoDespesas.fecharBanco();

        this.atualizarDadosTela();
    }

    public void atualizarDadosTela(){
        ArrayList<Movimentacao> receitas = new ArrayList<>();
        Double valorTotalReceitas = 0.0;
        for (Movimentacao receita:this.todasAsReceitas){
            if(receita.getData().get(Calendar.MONTH)==this.dataVisualizacao.get(Calendar.MONTH) &&
                    receita.getData().get(Calendar.YEAR)==this.dataVisualizacao.get(Calendar.YEAR)){
                receitas.add(receita);
                valorTotalReceitas+=receita.getValor();
            }
        }
        listaReceitas.setAdapter(new AdapterListView(this.getBaseContext(), receitas));
        totalReceitas.setText("R$" + String.format("%.2f", valorTotalReceitas));

        ArrayList<Movimentacao> despesas = new ArrayList<>();
        Double valorTotalDespesas = 0.0;
        for (Movimentacao despesa:this.todasAsDespesas){
            if(despesa.getData().get(Calendar.MONTH)==this.dataVisualizacao.get(Calendar.MONTH) &&
                    despesa.getData().get(Calendar.YEAR)==this.dataVisualizacao.get(Calendar.YEAR)){
                despesas.add(despesa);
                valorTotalDespesas+=despesa.getValor();
            }
        }
        listaDespesas.setAdapter(new AdapterListView(this.getBaseContext(), despesas));
        totalDespesas.setText("R$" + String.format("%.2f", valorTotalDespesas));


        Double valorTotalBalanco = valorTotalReceitas-valorTotalDespesas;
        int cor = (valorTotalBalanco < 0) ? getResources().getColor(R.color.POMEGRANATE) : getResources().getColor(R.color.PICTONBLUE);
        totalBalanco.setTextColor(cor);
        totalBalanco.setText("R$" + String.format("%.2f", valorTotalBalanco));
        this.dataVisualizacaoText.setText(this.meses[this.dataVisualizacao.get(Calendar.MONTH)]+" - "+this.dataVisualizacao.get(Calendar.YEAR));
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