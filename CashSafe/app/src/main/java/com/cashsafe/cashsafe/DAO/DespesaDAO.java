package com.cashsafe.cashsafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cashsafe.cashsafe.Util.MySQLiteHelper;
import com.cashsafe.cashsafe.modelo.CategoriaReceita;
import com.cashsafe.cashsafe.modelo.Despesa;
import com.cashsafe.cashsafe.modelo.Movimentacao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by aelx on 25/10/15.
 */
public class DespesaDAO {
    private MySQLiteHelper sqlHelper;
    private SQLiteDatabase db;

    public DespesaDAO(Context context) {
        this.sqlHelper = new MySQLiteHelper(context);
        this.abrirBanco();
    }
    public void abrirBanco(){
        db = sqlHelper.getWritableDatabase();
    }
    public void fecharBanco(){
        db.close();
    }

    public void inserirDespesa(Despesa despesa, String nomeCategoria) {
        ContentValues values = new ContentValues();
        values.put("valor", despesa.getValor());
        values.put("descricao", despesa.getDecricao());
        SimpleDateFormat formatador_saida =  new SimpleDateFormat("d/M/y");
        String data = formatador_saida.format(despesa.getData().getTime());
        values.put("data", data);
        System.out.println("S M " + despesa.getData().get(Calendar.MONTH) + " A " + despesa.getData().get(Calendar.YEAR));
        values.put("metodo_pagamento",despesa.getMetodoPagamento());
        values.put("categoria", nomeCategoria);
        db.insert("despesa", null, values);
    }
    public List<Despesa> getTodasDespesas() {
        List<Despesa> despesas = new ArrayList<Despesa>();
        Despesa despesa;
        Calendar cal;
        SimpleDateFormat formatador =  new SimpleDateFormat("d/M/y");
        Cursor cursor =  db.rawQuery("SELECT  * FROM despesa",null);
        if (cursor.moveToFirst()) {
            do {
                despesa = new Despesa();
                despesa.setId(cursor.getInt(0));
                despesa.setValor(cursor.getDouble(1));
                despesa.setDecricao(cursor.getString(2));
                cal = Calendar.getInstance();
                try {
                    cal.setTime(formatador.parse(cursor.getString(3)));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                despesa.setData(cal);

                despesa.setMetodoPagamento(cursor.getString(4));
                System.out.println("G M "+despesa.getData().get(Calendar.MONTH) + " A "+despesa.getData().get(Calendar.YEAR));
                despesa.setCategoria(new CategoriaReceita(cursor.getString(5)));
                despesas.add(despesa);
            } while (cursor.moveToNext());
        }
        return despesas;
    }

    public void editar(Despesa despesa,String categoria){
        SimpleDateFormat formatadorSaida =  new SimpleDateFormat("d/M/y");
        String data = formatadorSaida.format(despesa.getData().getTime());
        ContentValues values = new ContentValues();
        values.put("valor", String.valueOf(despesa.getValor()));
        values.put("descricao", despesa.getDecricao());
        values.put("data", data);
        values.put("categoria", categoria);
        values.put("metodo_pagamento",despesa.getMetodoPagamento());
        int i = db.update("despesa", values, "id" + " = ?", new String[]{ String.valueOf(despesa.getId())});
    }

    public void apagar(Despesa despesa){
        db.delete("despesa", "id"+" = ?",new String[]{ String.valueOf(despesa.getId())});
    }

    public double getSomaValores(){
        Cursor cursor =  db.rawQuery("SELECT  sum(despesa.valor) FROM despesa",null);
        if (cursor.moveToFirst()) {
            return cursor.getDouble(0);
        }
        return 0;
    }

    public HashMap getSomaValoresPorCategoria(){
        Cursor cursor =  db.rawQuery("SELECT  despesa.categoria,sum(despesa.valor) FROM despesa GROUP BY despesa.categoria;",null);
        HashMap<String, Double> resultado = new HashMap<String, Double>();
        if (cursor.moveToFirst()) {
            do {
                resultado.put(cursor.getString(0),cursor.getDouble(1));
            } while (cursor.moveToNext());
        }
        return resultado;
    }

    public List<Movimentacao> getTodasDespesasAsMovimentacoes() {
        List<Movimentacao> movimentacoes = new ArrayList<>();
        List<Despesa> despesas= this.getTodasDespesas();
        for(Movimentacao movimentacao:despesas){
            movimentacoes.add(movimentacao);
        }
        return movimentacoes;
    }
}
