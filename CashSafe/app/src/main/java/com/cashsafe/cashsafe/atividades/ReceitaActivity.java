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
import com.cashsafe.cashsafe.Util.ErroValidacao;
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

    public void okay(View view) throws ParseException {
        if (this.validar()==null){
            Receita receita = new Receita();
            receita.setValor(Double.parseDouble(valor.getText().toString()));
            receita.setDecricao(descricao.getText().toString());
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatadorEntrada =  new SimpleDateFormat("d/M/y");
            cal.setTime(formatadorEntrada.parse(data.getText().toString()));
            receita.setData(cal);
            ReceitaDAO dao = new ReceitaDAO(this.getBaseContext());
            dao.inserirReceita(receita,categorias.getSelectedItem().toString());
            this.finish();
        }
    }

}
