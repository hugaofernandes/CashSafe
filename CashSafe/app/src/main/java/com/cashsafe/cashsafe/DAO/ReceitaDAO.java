package com.cashsafe.cashsafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cashsafe.cashsafe.Util.MySQLiteHelper;
import com.cashsafe.cashsafe.modelo.Categoria;
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
        SimpleDateFormat formatadorSaida =  new SimpleDateFormat("y/M/d");
        String data = formatadorSaida.format(receita.getData().getTime());
        System.out.println("salvar data:"+data);
        values.put("data", data);
        values.put("categoria", nomeCategoria);
        db.insert("receita", null, values);

    }
    public List<Receita> getTodasReceitas() {
        List<Receita> receitas = new ArrayList<>();
        Receita receita;
        Calendar cal;
        SimpleDateFormat formatador =  new SimpleDateFormat("y/M/d");
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
                receita.setData(cal);
                receita.setCategoria(new CategoriaReceita(cursor.getString(4)));
                System.out.println(receita.getData().getTime());
                receitas.add(receita);
            } while (cursor.moveToNext());
        }
        return receitas;
    }
    public List<Receita> getReceitasPorMes(Calendar mes) {

        Calendar ultimoDiaMes = Calendar.getInstance();
        ultimoDiaMes.set(Calendar.DATE, mes.getActualMaximum(Calendar.DATE));

        Calendar primeiroDiaMes = Calendar.getInstance();
        primeiroDiaMes.set(Calendar.DATE, mes.getActualMinimum(Calendar.DATE));

        List<Receita> receitas = new ArrayList<Receita>();
        Receita receita;
        Calendar cal;

        SimpleDateFormat formatador =  new SimpleDateFormat("y/M/d");
        String primeiroDiaMesS = formatador.format(primeiroDiaMes.getTime());
        String ultimoDiaMesS = formatador.format(ultimoDiaMes.getTime());

        Cursor cursor =  db.rawQuery("SELECT  * FROM receita WHERE receita.data >= ? AND receita.data <= ?;",new String [] {primeiroDiaMesS,ultimoDiaMesS});
        if (cursor.moveToFirst()) {
            do {
                receita = new Receita();
                receita.setId(cursor.getInt(0));
                receita.setValor(cursor.getDouble(1));
                receita.setDecricao(cursor.getString(2));
                cal = Calendar.getInstance();
                try {
                    String time = cursor.getString(3);
                    System.out.println(time);
                    cal.setTime(formatador.parse(time));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                receita.setData(cal);
                receita.setCategoria(new CategoriaReceita(cursor.getString(5)));
                receitas.add(receita);
            } while (cursor.moveToNext());
        }
        return receitas;
    }
    public void editar(Receita receita,String categoria){
        SimpleDateFormat formatadorSaida =  new SimpleDateFormat("y/M/d");
        String data = formatadorSaida.format(receita.getData().getTime());
        ContentValues values = new ContentValues();
        values.put("valor", String.valueOf(receita.getValor()));
        values.put("descricao", receita.getDecricao());
        values.put("data", data);

        values.put("categoria", categoria);
        int i = db.update("receita", values, "id" + " = ?", new String[]{ String.valueOf(receita.getId())});
    }
    public void apagar(Receita receita){
        db.delete("receita", "id" + " = ?", new String[]{String.valueOf(receita.getId())});
    }
    public double getSomaValores(){
        Cursor cursor =  db.rawQuery("SELECT  sum(receita.valor) FROM receita",null);
        if (cursor.moveToFirst()) {
                return cursor.getDouble(0);
        }
        return 0;
    }
    public double getSomaReceitaPorMes(Calendar mes) {
        Calendar ultimoDiaMes = Calendar.getInstance();
        ultimoDiaMes.set(Calendar.DATE, mes.getActualMaximum(Calendar.DATE));

        Calendar primeiroDiaMes = Calendar.getInstance();
        primeiroDiaMes.set(Calendar.DATE, mes.getActualMinimum(Calendar.DATE));

        SimpleDateFormat formatador =  new SimpleDateFormat("y/M/d");
        String primeiroDiaMesS = formatador.format(primeiroDiaMes.getTime());
        String ultimoDiaMesS = formatador.format(ultimoDiaMes.getTime());

        Cursor cursor =  db.rawQuery("SELECT  SUM(receita.valor) FROM receita WHERE receita.data >= ? AND receita.data <= ?;",new String [] {primeiroDiaMesS,ultimoDiaMesS});

        if (cursor.moveToFirst())
            return cursor.getDouble(0);
        return 0.0;
    }

    public HashMap getSomaValoresPorCategoria(){
        Cursor cursor =  db.rawQuery("SELECT  receita.categoria,sum(receita.valor) FROM receita GROUP BY receita.categoria;", null);
        HashMap<String, Double> resultado = new HashMap<String, Double>();
        if (cursor.moveToFirst()) {
            do {
                resultado.put(cursor.getString(0),cursor.getDouble(1));
            } while (cursor.moveToNext());
        }
        return resultado;
    }

    public HashMap getSomaValoresPorCategoria(Calendar mes){
        Calendar ultimoDiaMes = Calendar.getInstance();
        ultimoDiaMes.set(Calendar.DATE, mes.getActualMaximum(Calendar.DATE));

        Calendar primeiroDiaMes = Calendar.getInstance();
        primeiroDiaMes.set(Calendar.DATE, mes.getActualMinimum(Calendar.DATE));

        SimpleDateFormat formatador =  new SimpleDateFormat("y/M/d");
        String primeiroDiaMesS = formatador.format(primeiroDiaMes.getTime());
        String ultimoDiaMesS = formatador.format(ultimoDiaMes.getTime());

        Cursor cursor =  db.rawQuery("SELECT  receita.categoria,sum(receita.valor) FROM receita WHERE receita.data >= ? AND receita.data <= ? GROUP BY receita.categoria;",
                new String [] {primeiroDiaMesS,ultimoDiaMesS});
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
