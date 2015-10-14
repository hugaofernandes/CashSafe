package com.cashsafe.cashsafe;

/**
 * Created by hugo on 12/10/15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        // SQL statement to create book table
        String categoriaDespesa = "create table categoriaDespesas(" +
                "categoriaId integer primary key autoincrement," +
                "nome text not null";

        String categoriaReceita = "create table categoriaReceitas(" +
                "categoriaId integer primary key autoincrement," +
                "nome text not null";

        String despesa = "create table despesa(" +
                "despesaId integer primary key autoincrement," +
                "valor double not null" +
                "dataVencimento text not null," +
                "descricao text not null," +
                "categoria text," +
                "formaPagamento text not null," +
                "pago text," +
                "despesaFixa text," +
                "foreign key (categoria) references categoriaDespesas(nome));";

        String receita = "create table receita(" +
                "receitaId integer primary key autoincrement," +
                "valor double not null" +
                "dataRecebimento text not null," +
                "descricao text not null," +
                "categoria text," +
                "recebido text," +
                "receitaFixa text," +
                "foreign key (categoria) references categoriaReceitas(nome));";

        // create books table
        db.execSQL(categoriaDespesa);
        db.execSQL(categoriaReceita);
        db.execSQL(despesa);
        db.execSQL(receita);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS cashsafe");

        // create fresh books table
        this.onCreate(db);
    }

    public void addDespesa(Despesa despesa){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", despesa.getCategoria());
        db.insert("categoriaDespesa", null, values);
        values = new ContentValues();
        values.put("valor", despesa.getValor());
        values.put("categoria", despesa.getCategoria());
        values.put("descricao", despesa.getDecricao());
        values.put("despesaFixa", despesa.getFixa());
        values.put("pago", despesa.getPago());
        values.put("formaPagamento", despesa.getPagamento());
        values.put("dataVencimento", despesa.getVencimento());
        db.insert("despesa", null, values);
        db.close();
    }

}
