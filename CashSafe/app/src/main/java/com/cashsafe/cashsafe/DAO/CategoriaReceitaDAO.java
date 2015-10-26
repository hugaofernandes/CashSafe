package com.cashsafe.cashsafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cashsafe.cashsafe.Util.MySQLiteHelper;
import com.cashsafe.cashsafe.modelo.Categoria;
import com.cashsafe.cashsafe.modelo.CategoriaReceita;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aelx on 24/10/15.
 */
public class CategoriaReceitaDAO {
    private MySQLiteHelper sqlHelper;
    private SQLiteDatabase db;

    public CategoriaReceitaDAO(Context context) {
        this.sqlHelper = new MySQLiteHelper(context);
    }

    public void inserirCategoriaReceita(CategoriaReceita categoria) {
        db = sqlHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", categoria.getNome());
        values.put("tipo",Categoria.tipo_categorias.receita.toString());
        db.insert("categoria", null, values);
        System.out.println("inserida receita");
        db.close();
    }
    public List<CategoriaReceita> getTodasCategoriasReceitas() {
        List<CategoriaReceita> categorias = new LinkedList<CategoriaReceita>();
        db = sqlHelper.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT  * FROM categoria where categoria.tipo = @0 and ativo = @1", new String[]{ Categoria.tipo_categorias.receita.toString() ,String.valueOf(1)});
        if (cursor.moveToFirst()) {
            do {
                categorias.add(new CategoriaReceita(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        return categorias;
    }
    public void apagar(String nome){
        db = sqlHelper.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT count(*) FROM receita where categoria = @0;",new String[]{nome});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        if (count == 0){
            db.delete("categoria", "nome" + " = ?", new String[]{nome});
        }
        else{
            ContentValues values = new ContentValues();
            values.put("nome", nome);
            values.put("tipo", Categoria.tipo_categorias.receita.toString());
            values.put("ativo", 0);
            int i = db.update("categoria", values, "nome" + " = ?", new String[]{nome});
        }
    }
    public List<Categoria> getTodasAsCategorias(){
        List<Categoria> categorias = new ArrayList<>();
        List<CategoriaReceita> categoriaDespesas = this.getTodasCategoriasReceitas();
        for(Categoria categoria:categoriaDespesas){
            categorias.add(categoria);
        }
        return categorias;
    }
    public List<String> getTodosOsNomes() {
        List<CategoriaReceita> categorias = this.getTodasCategoriasReceitas();
        List<String> nomes = new ArrayList<String>();
        for (CategoriaReceita categoria : categorias) {
            nomes.add(categoria.getNome());
        }
        return nomes;
    }
}
