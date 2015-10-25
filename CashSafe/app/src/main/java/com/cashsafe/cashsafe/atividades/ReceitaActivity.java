package com.cashsafe.cashsafe.atividades;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.cashsafe.cashsafe.DAO.CategoriaReceitaDAO;
import com.cashsafe.cashsafe.DAO.ReceitaDAO;
import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.modelo.Despesa;
import com.cashsafe.cashsafe.modelo.Receita;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReceitaActivity extends AppCompatActivity {

    private EditText valor;
    private EditText data;
    private EditText descricao;
    private Spinner categorias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.PICTONBLUE)));


        categorias = (Spinner) findViewById(R.id.categorias_receita);


        CategoriaReceitaDAO despesaDAO = new CategoriaReceitaDAO(getBaseContext());
        List<String> nomeCategorias = despesaDAO.getTodosOsNomes();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomeCategorias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(dataAdapter);

        valor = (EditText)findViewById(R.id.valor_receita);
        descricao = (EditText) findViewById(R.id.descricao_receita);
        data = (EditText) findViewById(R.id.data_receita);

    }

    public void cancelar(View view) {
        this.finish();
    }

    public void okay(View view) throws ParseException {
        Receita receita = new Receita();
        receita.setValor(Double.parseDouble(valor.getText().toString()));
        receita.setDecricao(descricao.getText().toString());
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat formatadorEntrada = new SimpleDateFormat("DD/MM/yyyy");
        cal.setTime(formatadorEntrada.parse(data.getText().toString()));
        receita.setData(cal);
        ReceitaDAO dao = new ReceitaDAO(this.getBaseContext());
        dao.inserirReceita(receita,categorias.getSelectedItem().toString());
        this.finish();
    }

}
