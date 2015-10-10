package com.cashsafe.cashsafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Intent intent = getIntent();

    }

    public void balancoMensal(View view) {
        Intent intent = new Intent(this, BalancoActivity.class);
        startActivity(intent);
    }

    public void receitasPorCategoria(View view) {
        Intent intent = new Intent(this, ReceitaCategoriaActivity.class);
        startActivity(intent);
    }

    public void despesasPorCategoria(View view) {
        Intent intent = new Intent(this, DespesasCategoriaActivity.class);
        startActivity(intent);
    }

    public void cadastroReceita(View view) {
        Intent intent = new Intent(this, ReceitaActivity.class);
        startActivity(intent);
    }

    public void cadastroDespesa(View view) {
        Intent intent = new Intent(this, DespesaActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
