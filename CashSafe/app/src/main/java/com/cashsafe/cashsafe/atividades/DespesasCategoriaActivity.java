package com.cashsafe.cashsafe.atividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.cashsafe.cashsafe.DAO.CategoriaDespesaDAO;
import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.modelo.CategoriaDespesa;

public class DespesasCategoriaActivity extends AppCompatActivity {
    private EditText nome_categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas_categoria);
        nome_categoria = (EditText)findViewById(R.id.nome_categoria);

    }
    public void cancelar(View view) {
        this.finish();
    }

    public void okay(View view) {
        CategoriaDespesa categoria = new CategoriaDespesa(nome_categoria.getText().toString());
        CategoriaDespesaDAO dao = new CategoriaDespesaDAO(this.getBaseContext());
        dao.inserirCategoriaDespesa(categoria);
        this.finish();
    }
}
