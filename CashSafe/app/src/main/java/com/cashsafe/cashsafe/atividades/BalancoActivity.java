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
        Intent intent = getIntent();
        ReceitaDAO dao = new ReceitaDAO(this.getBaseContext());
        ArrayList<Movimentacao> movimentacoes = (ArrayList<Movimentacao>) dao.getTodasReceitasAsMovimentacoes();

        Log.d(PrincipalActivity.class.getSimpleName(), "getting list");
        ListView list= (ListView)findViewById( R.id.lista_receitas);
        AdapterListView adapter = new AdapterListView(this.getBaseContext(), movimentacoes);
        Log.d(PrincipalActivity.class.getSimpleName(), "set adapter");
        list.setAdapter(adapter);
    }
}