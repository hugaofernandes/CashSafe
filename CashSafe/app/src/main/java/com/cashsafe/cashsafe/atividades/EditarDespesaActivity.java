package com.cashsafe.cashsafe.atividades;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.cashsafe.cashsafe.DAO.CategoriaDespesaDAO;
import com.cashsafe.cashsafe.DAO.DespesaDAO;
import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.Util.ErroValidacao;
import com.cashsafe.cashsafe.modelo.CategoriaDespesa;
import com.cashsafe.cashsafe.modelo.Despesa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EditarDespesaActivity extends AppCompatActivity {
    private EditText valor;
    private EditText data;
    private EditText descricao;
    private Spinner categorias;
    private Despesa despesa;
    private Spinner pagamento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_despesa);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.POMEGRANATE)));

        Intent intent = getIntent();
        this.despesa = (Despesa) intent.getParcelableExtra("despesa");
        categorias = (Spinner) findViewById(R.id.categorias_despesa);

        CategoriaDespesaDAO despesaDAO = new CategoriaDespesaDAO(getBaseContext());
        List<String> nomeCategorias = despesaDAO.getTodosOsNomes();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomeCategorias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(dataAdapter);

        valor = (EditText)findViewById(R.id.editar_valor_despesa);
        descricao = (EditText) findViewById(R.id.editar_descricao_despesa);
        data = (EditText) findViewById(R.id.editar_data_despesa);
        pagamento = (Spinner) findViewById(R.id.metodos_pagamento);

        List<String> metodoPagamento = new ArrayList<String>();
        metodoPagamento.add("Dinheiro");
        metodoPagamento.add("Cartao");
        ArrayAdapter<String> metodoPagamentoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, metodoPagamento);
        metodoPagamentoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pagamento.setAdapter(metodoPagamentoAdapter);

        SimpleDateFormat formatador =  new SimpleDateFormat("d/M/y");
        String dataText = formatador.format(despesa.getData().getTime());

        valor.setText(String.valueOf(despesa.getValor()));
        descricao.setText(despesa.getDecricao());
        data.setText(dataText);
        pagamento.setSelection(metodoPagamento.indexOf(despesa.getMetodoPagamento()));
        categorias.setSelection(nomeCategorias.indexOf(despesa.getCategoria().getNome()));
    }
    public ErroValidacao validar(){
        if (valor.getText().toString().isEmpty()){
            valor.setError(ErroValidacao.CAMPO_OBRIGATORIO.getDescricao());
            return ErroValidacao.CAMPO_OBRIGATORIO;
        }
        if (data.getText().toString().isEmpty()){
            data.setError(ErroValidacao.CAMPO_OBRIGATORIO.getDescricao());
            return ErroValidacao.CAMPO_OBRIGATORIO;
        }
        try {
            Double.parseDouble(valor.getText().toString());
        }catch (Exception e){
            valor.setError(ErroValidacao.VALOR_INVALIDO.getDescricao());
            return ErroValidacao.VALOR_INVALIDO;
        }
        SimpleDateFormat formatadorEntrada =  new SimpleDateFormat("d/M/y");
        try{
            formatadorEntrada.parse(data.getText().toString());
        }catch (Exception e){
            data.setError(ErroValidacao.DATA_INVALIDA.getDescricao());
            return ErroValidacao.DATA_INVALIDA;
        }
        return null;
    }

    public void salvar(View view){
        if (this.validar()==null) {
            despesa.setValor(Double.parseDouble(valor.getText().toString()));
            despesa.setDecricao(descricao.getText().toString());
            despesa.setMetodoPagamento(pagamento.getSelectedItem().toString());
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatadorEntrada = new SimpleDateFormat("d/M/y");
            try {
                cal.setTime(formatadorEntrada.parse(data.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            despesa.setData(cal);
            DespesaDAO dao = new DespesaDAO(this.getBaseContext());
            dao.editar(despesa, categorias.getSelectedItem().toString());
            dao.fecharBanco();
            this.finish();
        }
    }

    public void cancelar(View view){
        this.finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar_despesa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.apagar_item) {
            DespesaDAO dao = new DespesaDAO(this.getBaseContext());
            dao.apagar(despesa);
            dao.fecharBanco();
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
