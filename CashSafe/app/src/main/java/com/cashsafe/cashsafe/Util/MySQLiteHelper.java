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
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "cashsafe";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("creating");
        String categoria = "CREATE TABLE categoria ( nome text primary key ,tipo text not null, ativo integer not null);";
        db.execSQL(categoria);
        //adicionando categoriaDespesa padrão
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
            values.put("ativo",1);
            db.insert("categoria", null, values);
        }
        //adicionando categoriaDespesa padrão

        categoriasDefault = new ArrayList<String>();
        categoriasDefault.add("Trabalho");
        categoriasDefault.add("Emprestimos");
        categoriasDefault.add("Trabalho noturno");

        values = new ContentValues();
        for(String nomeCategoria:categoriasDefault){
            values.put("nome",nomeCategoria);
            values.put("tipo", Categoria.tipo_categorias.receita.toString());
            values.put("ativo",1);
            db.insert("categoria", null, values);
        }

        String despesa = "CREATE TABLE despesa ( id integer primary key autoincrement" +
                ",valor text not null," +
                "descricao text not null," +
                "data text not null," +
                "metodo_pagamento text not null," +
                "categoria text not null," +
                "foreign key (categoria) references categoria(nome));";
        db.execSQL(despesa);

        String receita = "CREATE TABLE receita ( id integer primary key autoincrement" +
                ",valor text not null," +
                "descricao text not null," +
                "data text not null," +
                "categoria text not null," +
                "foreign key (categoria) references categoria(nome));";
        db.execSQL(receita);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS categoria");
        db.execSQL("DROP TABLE IF EXISTS despesa");
        db.execSQL("DROP TABLE IF EXISTS receita");
        this.onCreate(db);
    }


}
