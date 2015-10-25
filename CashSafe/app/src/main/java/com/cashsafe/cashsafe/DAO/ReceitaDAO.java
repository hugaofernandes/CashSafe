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
    }

    public void inserirReceita(Receita receita,String nomeCategoria) {
        db = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("valor", receita.getValor());
        System.out.println(receita.getValor());
        values.put("descricao", receita.getDecricao());
        System.out.println(receita.getDecricao());
        SimpleDateFormat formatador_saida = new SimpleDateFormat("DD/MM/yyyy");
        String data = formatador_saida.format(receita.getData().getTime());
        System.out.println(data);
        values.put("data", data);
        values.put("categoria", nomeCategoria);
        System.out.println(nomeCategoria);
        db.insert("receita", null, values);
        db.close();
    }
    public List<Receita> getTodasReceitas() {
        List<Receita> receitas = new LinkedList<Receita>();
        db = sqlHelper.getWritableDatabase();
        Receita receita;
        Calendar cal;
        SimpleDateFormat formatador = new SimpleDateFormat("DD/MM/yyyy");
        Cursor cursor =  db.rawQuery("SELECT  * FROM receita",null);
        if (cursor.moveToFirst()) {
            do {
                System.out.println("------");
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

    public List<Movimentacao> getTodasReceitasAsMovimentacoes() {
        List<Movimentacao> movimentacoes = new ArrayList<>();
        List<Receita> receitas= this.getTodasReceitas();
        for(Movimentacao movimentacao:receitas){
            movimentacoes.add(movimentacao);
        }
        return movimentacoes;
    }
}
