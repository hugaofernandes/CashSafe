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

import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.Util.AdapterListView;
import com.cashsafe.cashsafe.modelo.Despesa;

import java.util.ArrayList;
import java.util.Date;

public class BalancoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balanco);
        Intent intent = getIntent();
        ArrayList<Despesa> despesas = new ArrayList<>();
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca1", 25.0));
        despesas.add(new Despesa(new Date(),true,"dinheiro",true,"saúde","doenca1",50.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca2", 75.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca3", 100.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca4", 25.0));
        despesas.add(new Despesa(new Date(),true,"dinheiro",true,"saúde","doenca5",50.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca6", 75.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca7", 100.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca8", 25.0));
        despesas.add(new Despesa(new Date(),true,"dinheiro",true,"saúde","doenca9",50.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca10", 75.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca11", 100.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca12", 25.0));
        despesas.add(new Despesa(new Date(),true,"dinheiro",true,"saúde","doenca13",50.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca14", 75.0));
        despesas.add(new Despesa(new Date(), true, "dinheiro", true, "saúde", "doenca15", 100.0));
        Log.d(PrincipalActivity.class.getSimpleName(), "getting list");
        ListView list= (ListView)findViewById( R.id.lista_receitas);
        AdapterListView adapter = new AdapterListView(this.getBaseContext(), despesas);
        Log.d(PrincipalActivity.class.getSimpleName(), "set adapter");
        list.setAdapter(adapter);
    }
}