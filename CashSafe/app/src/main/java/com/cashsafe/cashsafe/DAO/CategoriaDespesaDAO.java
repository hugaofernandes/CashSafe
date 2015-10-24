package com.cashsafe.cashsafe.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cashsafe.cashsafe.Util.MySQLiteHelper;
import com.cashsafe.cashsafe.modelo.Categoria;
import com.cashsafe.cashsafe.modelo.CategoriaDespesa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aelx on 23/10/15.
 */
public class CategoriaDespesaDAO {

    private MySQLiteHelper sqlHelper;
    private SQLiteDatabase db;

    public CategoriaDespesaDAO(Context context) {
        this.sqlHelper = new MySQLiteHelper(context);
        Log.d(this.getClass().getSimpleName(), "created categoria DAO");
    }
    public void inserirCategoriaDespesa(CategoriaDespesa categoria) {

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
    public List<CategoriaDespesa> getTodasCategoriasDespesas() {
        List<CategoriaDespesa> categorias = new LinkedList<CategoriaDespesa>();

        Log.d(this.getClass().getSimpleName(), "getting all categories");



        db = sqlHelper.getWritableDatabase();
        Cursor cursor =  db.rawQuery("SELECT  * FROM categoria where categoria.tipo = @0", new String[]{ Categoria.tipo_categorias.despesa.toString() });


        if (cursor.moveToFirst()) {
            do {
                categorias.add(new CategoriaDespesa(cursor.getString(0)));
            } while (cursor.moveToNext());
        }

        Log.d(this.getClass().getSimpleName(), "gotten all categories");

        return categorias;
    }
    public List<String> getTodosOsNomes() {
        List<CategoriaDespesa> categorias = this.getTodasCategoriasDespesas();
        List<String> nomes = new ArrayList<String>();
        for(CategoriaDespesa categoria:categorias){
                nomes.add(categoria.getNome());
        }
        return nomes;
    }
}