package com.cashsafe.cashsafe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DespesaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText valor;
    private EditText vencimento;
    private EditText descricao;
    private Spinner categoria;
    private Spinner pagamento;
    private CheckBox fixa;
    private CheckBox pago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesa);

        Intent intent = getIntent();

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Lazer");
        categories.add("Alimentação");
        categories.add("Aluguel");
        categories.add("Educação");
        categories.add("Energia");
        categories.add("Transporte");
        categories.add("Saúde");

        List<String> categories2 = new ArrayList<String>();
        categories2.add("Dinheiro");
        categories2.add("Cartao");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories2);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        spinner2.setAdapter(dataAdapter2);

        valor = (EditText)findViewById(R.id.edit_message);
        vencimento = (EditText)findViewById(R.id.etxt_fromdate);
        descricao = (EditText) findViewById(R.id.editText2);
        categoria = (Spinner) findViewById(R.id.spinner);
        pagamento = (Spinner) findViewById(R.id.spinner2);
        pago = (CheckBox) findViewById(R.id.checkBox);
        fixa = (CheckBox) findViewById(R.id.checkBox2);

    }

    public void cancelar(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

    public void okay(View view) throws ParseException {
        Despesa despesa = new Despesa();
        despesa.setValor(Double.parseDouble(valor.getText().toString()));
        despesa.setCategoria(categoria.getSelectedItem().toString());
        despesa.setDecricao(descricao.getText().toString());
        despesa.setFixa("nao");
        if (fixa.isEnabled()){
            despesa.setFixa("sim");
        }
        despesa.setPagamento(pagamento.getSelectedItem().toString());
        despesa.setVencimento(vencimento.getText().toString());
        despesa.setPago("nao");
        if (pago.isEnabled()){
            despesa.setPago("sim");
        }

        MySQLiteHelper db = new MySQLiteHelper(this);
        db.addDespesa(despesa);

        Intent intent = new Intent(this, PrincipalActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

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
