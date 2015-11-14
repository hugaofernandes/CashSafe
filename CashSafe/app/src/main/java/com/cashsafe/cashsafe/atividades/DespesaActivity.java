package com.cashsafe.cashsafe.atividades;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.cashsafe.cashsafe.DAO.CategoriaDespesaDAO;
import com.cashsafe.cashsafe.DAO.DespesaDAO;
import com.cashsafe.cashsafe.modelo.Despesa;
import com.cashsafe.cashsafe.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DespesaActivity extends AppCompatActivity {

    private EditText valor;
    private EditText data;
    private EditText descricao;
    private Spinner categorias;
    private Spinner pagamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.POMEGRANATE)));

        categorias = (Spinner) findViewById(R.id.categorias_despesa);
        CategoriaDespesaDAO despesaDAO = new CategoriaDespesaDAO(getBaseContext());
        List<String> categoriasNomes = despesaDAO.getTodosOsNomes();
        ArrayAdapter<String> categoriasAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriasNomes);
        categoriasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(categoriasAdapter);

        pagamento = (Spinner) findViewById(R.id.metodos_pagamento);
        List<String> metodoPagamento = new ArrayList<String>();
        metodoPagamento.add("Dinheiro");
        metodoPagamento.add("Cartao");
        ArrayAdapter<String> metodoPagamentoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, metodoPagamento);
        metodoPagamentoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pagamento.setAdapter(metodoPagamentoAdapter);

        valor = (EditText)findViewById(R.id.valor_despesa);
        data = (EditText)findViewById(R.id.data_despesa);
        descricao = (EditText) findViewById(R.id.descricao_despesa);

    }

    public void cancelar(View view) {
        this.finish();
    }

    public void okay(View view) throws ParseException {
        Despesa despesa = new Despesa();
        despesa.setValor(Double.parseDouble(valor.getText().toString()));
        despesa.setDecricao(descricao.getText().toString());

        despesa.setMetodoPagamento(pagamento.getSelectedItem().toString());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatadorEntrada = new SimpleDateFormat("d/M/y");
        cal.setTime(formatadorEntrada.parse(data.getText().toString()));
        despesa.setData(cal);

        DespesaDAO dao = new DespesaDAO(this.getBaseContext());
        dao.inserirDespesa(despesa, categorias.getSelectedItem().toString());
        this.finish();
    }

}
