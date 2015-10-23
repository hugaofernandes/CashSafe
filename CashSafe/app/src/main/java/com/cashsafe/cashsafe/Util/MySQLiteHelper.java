package com.cashsafe.cashsafe.Util;

/**
 * Created by hugo on 12/10/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "cashsafe";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(this.getClass().getSimpleName(), "created MysqlHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String categoriaDespesa = "CREATE TABLE categoriaDespesas ( nome text primary key );";


        Log.d(this.getClass().getSimpleName(), "----- creating table categoria-------");
        db.execSQL(categoriaDespesa);
        Log.d(this.getClass().getSimpleName(), "----- created table categoria-------");

        Log.d(this.getClass().getSimpleName(), "----- adding default categories------");

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
            db.insert("categoriaDespesas", null, values);
        }
        Log.d(this.getClass().getSimpleName(), "----- added default categories------");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d(this.getClass().getSimpleName(), "onUpgrade() executing drop queries");
        db.execSQL("DROP TABLE IF EXISTS categoriaDespesas");
        db.execSQL("DROP TABLE IF EXISTS categoriaReceitas");
        db.execSQL("DROP TABLE IF EXISTS despesa");
        db.execSQL("DROP TABLE IF EXISTS receita");
        Log.d(this.getClass().getSimpleName(), "onUpgrade() calling onCreate(db)");
        this.onCreate(db);
        Log.d(this.getClass().getSimpleName(), "onUpgrade() called onCreate(db)");
    }


}
