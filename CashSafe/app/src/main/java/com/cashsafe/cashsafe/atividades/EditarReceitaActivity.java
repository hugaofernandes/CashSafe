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

import com.cashsafe.cashsafe.DAO.CategoriaReceitaDAO;
import com.cashsafe.cashsafe.DAO.ReceitaDAO;
import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.Util.ErroValidacao;
import com.cashsafe.cashsafe.modelo.Receita;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class EditarReceitaActivity extends AppCompatActivity {
    private EditText valor;
    private EditText data;
    private EditText descricao;
    private Spinner categorias;
    private Receita receita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_receita);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.PICTONBLUE)));
        Intent intent = getIntent();
        this.receita = (Receita) intent.getParcelableExtra("receita");
        categorias = (Spinner) findViewById(R.id.categorias_receita);

        CategoriaReceitaDAO despesaDAO = new CategoriaReceitaDAO(getBaseContext());
        List<String> nomeCategorias = despesaDAO.getTodosOsNomes();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nomeCategorias);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorias.setAdapter(dataAdapter);

        valor = (EditText)findViewById(R.id.editar_valor_receita);
        descricao = (EditText) findViewById(R.id.editar_descricao_receita);
        data = (EditText) findViewById(R.id.editar_data_receita);

        SimpleDateFormat formatador =  new SimpleDateFormat("d/M/y");
        String dataText = formatador.format(receita.getData().getTime());
        valor.setText(String.valueOf(receita.getValor()));
        descricao.setText(receita.getDecricao());
        data.setText(dataText);
        categorias.setSelection(nomeCategorias.indexOf(receita.getCategoria().getNome()));
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
            receita.setValor(Double.parseDouble(valor.getText().toString()));
            receita.setDecricao(descricao.getText().toString());
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formatadorEntrada = new SimpleDateFormat("d/M/y");
            try {
                cal.setTime(formatadorEntrada.parse(data.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            receita.setData(cal);
            ReceitaDAO dao = new ReceitaDAO(this.getBaseContext());
            dao.editar(receita, categorias.getSelectedItem().toString());
            dao.fecharBanco();
            this.finish();
        }
    }

    public void cancelar(View view){
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editar_receita, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.apagar_item) {
            ReceitaDAO dao = new ReceitaDAO(this.getBaseContext());
            dao.apagar(receita);
            dao.fecharBanco();
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
