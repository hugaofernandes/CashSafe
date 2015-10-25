package com.cashsafe.cashsafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cashsafe.cashsafe.Util.MySQLiteHelper;
import com.cashsafe.cashsafe.modelo.CategoriaDespesa;
import com.cashsafe.cashsafe.modelo.CategoriaReceita;
import com.cashsafe.cashsafe.modelo.Movimentacao;
import com.cashsafe.cashsafe.modelo.Receita;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aelx on 24/10/15.
 */
public class ReceitaDAO {
    private MySQLiteHelper sqlHelper;
    private SQLiteDatabase db;

    public ReceitaDAO(Context context) {
        this.sqlHelper = new MySQLiteHelper(context);
        this.abrirBanco();
    }

    public void abrirBanco(){
        db = sqlHelper.getWritableDatabase();
    }
    public void fecharBanco(){
        db.close();
    }

    public void inserirReceita(Receita receita,String nomeCategoria) {
        ContentValues values = new ContentValues();
        values.put("valor", receita.getValor());
        values.put("descricao", receita.getDecricao());
        SimpleDateFormat formatadorSaida = new SimpleDateFormat("DD/MM/yyyy");
        String data = formatadorSaida.format(receita.getData().getTime());
        values.put("data", data);
        values.put("categoria", nomeCategoria);
        db.insert("receita", null, values);

    }
    public List<Receita> getTodasReceitas() {
        List<Receita> receitas = new LinkedList<Receita>();
        Receita receita;
        Calendar cal;
        SimpleDateFormat formatador = new SimpleDateFormat("DD/MM/yyyy");
        Cursor cursor =  db.rawQuery("SELECT  * FROM receita", null);
        if (cursor.moveToFirst()) {
            do {
                receita = new Receita();
                receita.setId(cursor.getInt(0));
                receita.setValor(cursor.getDouble(1));
                receita.setDecricao(cursor.getString(2));
                cal = Calendar.getInstance();
                try {
                    cal.setTime(formatador.parse(cursor.getString(3)));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                receita.setCategoria(new CategoriaReceita(cursor.getString(4)));
                receitas.add(receita);
            } while (cursor.moveToNext());
        }
        return receitas;
    }

    public double getSomaValores(){
        Cursor cursor =  db.rawQuery("SELECT  sum(receita.valor) FROM receita",null);
        if (cursor.moveToFirst()) {
                return cursor.getDouble(0);
        }
        return 0;
    }

    public HashMap getSomaValoresPorCategoria(){
        Cursor cursor =  db.rawQuery("SELECT  receita.categoria,sum(receita.valor) FROM receita GROUP BY receita.categoria;",null);
        HashMap<String, Double> resultado = new HashMap<String, Double>();
        if (cursor.moveToFirst()) {
            do {
                resultado.put(cursor.getString(0),cursor.getDouble(1));
            } while (cursor.moveToNext());
        }
        return resultado;
    }

    public List<Movimentacao> getTodasReceitasAsMovimentacoes() {
        List<Movimentacao> movimentacoes = new ArrayList<>();
        List<Receita> receitas= this.getTodasReceitas();
        for(Movimentacao movimentacao:receitas){
            movimentacoes.add(movimentacao);
        }
        return movimentacoes;
    }
}
