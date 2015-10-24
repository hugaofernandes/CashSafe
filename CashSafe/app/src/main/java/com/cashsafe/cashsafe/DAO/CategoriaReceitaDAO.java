package com.cashsafe.cashsafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
        Log.d(this.getClass().getSimpleName(), "created categoria DAO");
    }
    public void inserirCategoriaReceita(CategoriaReceita categoria) {

        Log.d(this.getClass().getSimpleName(), "calling sqlHelper.getWritableDatabase()");
        db = sqlHelper.getWritableDatabase();
        Log.d(this.getClass().getSimpleName(), "called sqlHelper.getWritableDatabase()");
        Log.d(this.getClass().getSimpleName(), "adding category");
        ContentValues values = new ContentValues();
        values.put("nome", categoria.getNome());
        db.insert("categoriaDespesas", null, values);
        db.close();
        Log.d(this.getClass().getSimpleName(), "added category");
    }
    public List<CategoriaReceita> getTodasCategoriasDespesas() {
        List<CategoriaReceita> categorias = new LinkedList<CategoriaReceita>();

        Log.d(this.getClass().getSimpleName(), "getting all categories");

        db = sqlHelper.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT  * FROM categoria where categoria.tipo = @0", new String[]{ Categoria.tipo_categorias.receita.toString() });


        if (cursor.moveToFirst()) {
            do {
                categorias.add(new CategoriaReceita(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        Log.d(this.getClass().getSimpleName(), "gotten all categories");

        return categorias;
    }
    public List<String> getTodosOsNomes() {
        List<CategoriaReceita> categorias = this.getTodasCategoriasDespesas();
        List<String> nomes = new ArrayList<String>();
        for (CategoriaReceita categoria : categorias) {
            nomes.add(categoria.getNome());
        }
        return nomes;
    }
}
