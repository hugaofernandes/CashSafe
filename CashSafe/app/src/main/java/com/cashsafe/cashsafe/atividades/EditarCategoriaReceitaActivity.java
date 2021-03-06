package com.cashsafe.cashsafe.atividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.cashsafe.cashsafe.DAO.CategoriaReceitaDAO;
import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.modelo.CategoriaReceita;

public class EditarCategoriaReceitaActivity extends AppCompatActivity {
    String nomeCategoriaText;
    private EditText nome_categoria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categoria_receita);
        nomeCategoriaText = getIntent().getStringExtra("nome_categoria");
        nome_categoria = (EditText)findViewById(R.id.nome_categoria);
        nome_categoria.setText(nomeCategoriaText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editar_categoria_receita, menu);
        return true;
    }
    public void salvar(View view){
        //TODO: implementar metodo para salvar
        System.out.println("salvar");
        this.finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.apagar_item) {
            CategoriaReceitaDAO dao = new CategoriaReceitaDAO(this.getBaseContext());
            System.out.println("apagar");
            dao.apagar(nomeCategoriaText);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
