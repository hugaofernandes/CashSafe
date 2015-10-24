package com.cashsafe.cashsafe.Util;

/**
 * Created by hugo on 12/10/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.cashsafe.cashsafe.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "cashsafe";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(this.getClass().getSimpleName(), "created MysqlHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String categoriaDespesa = "CREATE TABLE categoria ( nome text primary key ,tipo text not null);";

                ;

        Log.d(this.getClass().getSimpleName(), "----- creating table categoria-------");
        db.execSQL(categoriaDespesa);
        Log.d(this.getClass().getSimpleName(), "----- created table categoria-------");

        Log.d(this.getClass().getSimpleName(), "----- adding default despesa categories------");

        List<String> categoriasDefault = new ArrayList<String>();
        categoriasDefault.add("Lazer");
        categoriasDefault.add("Test");
        categoriasDefault.add("Alimentação");
        categoriasDefault.add("Aluguel");
        categoriasDefault.add("Educação");
        categoriasDefault.add("Energia");
        categoriasDefault.add("Transporte");
        categoriasDefault.add("Saúde");
        ContentValues values = new ContentValues();
        for(String nomeCategoria:categoriasDefault){
            values.put("nome",nomeCategoria);
            values.put("tipo", Categoria.tipo_categorias.despesa.toString());
            db.insert("categoria", null, values);
        }

        Log.d(this.getClass().getSimpleName(), "----- added default despesa categories------");

        Log.d(this.getClass().getSimpleName(), "----- adding default receita categories------");

        categoriasDefault = new ArrayList<String>();
        categoriasDefault.add("Trabalho");
        categoriasDefault.add("Emprestimos");
        categoriasDefault.add("Trabalho noturno");

        values = new ContentValues();
        for(String nomeCategoria:categoriasDefault){
            values.put("nome",nomeCategoria);
            values.put("tipo", Categoria.tipo_categorias.receita.toString());
            db.insert("categoria", null, values);
        }

        Log.d(this.getClass().getSimpleName(), "----- added default receita categories------");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(this.getClass().getSimpleName(), "onUpgrade() executing drop queries");
        db.execSQL("DROP TABLE IF EXISTS categoria");
        db.execSQL("DROP TABLE IF EXISTS despesa");
        db.execSQL("DROP TABLE IF EXISTS receita");
        Log.d(this.getClass().getSimpleName(), "onUpgrade() calling onCreate(db)");
        this.onCreate(db);
        Log.d(this.getClass().getSimpleName(), "onUpgrade() called onCreate(db)");
    }


}
