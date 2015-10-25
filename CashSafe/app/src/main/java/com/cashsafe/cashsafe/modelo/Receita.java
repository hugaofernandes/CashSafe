package com.cashsafe.cashsafe.modelo;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aelx on 24/10/15.
 */
public class Receita extends Movimentacao{
    public Receita(Calendar data, CategoriaReceita categoria, String decricao, double valor) {
        super(data, categoria, decricao, valor);
    }

    public Receita() {
    }

    public Receita(Calendar data, String decricao, double valor) {
        super(data, decricao, valor);
    }

    public Receita(int id, double valor, String decricao, Calendar data, Categoria categoria) {
        super(id, valor, decricao, data, categoria);
    }

    private Receita(Parcel in) {
        String[] dados= new String[5];
        in.readStringArray(dados);
        SimpleDateFormat formatador = new SimpleDateFormat("DD/MM/yyyy");
        Calendar data = Calendar.getInstance();
        try {
            data.setTime(formatador.parse(dados[3]));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        this.setId(Integer.parseInt(dados[0]));
        this.setValor(Double.parseDouble(dados[1]));
        this.setDecricao(dados[2]);
        this.setData(data);
        this.setCategoria(new CategoriaReceita(dados[4]));

    }

    public static final Parcelable.Creator<Receita> CREATOR = new Parcelable.Creator<Receita>() {
        public Receita createFromParcel(Parcel in) {
            return new Receita(in);
        }

        public Receita[] newArray(int size) {
            return new Receita[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        SimpleDateFormat formatador = new SimpleDateFormat("DD/MM/yyyy");
        String data = formatador.format(this.getData().getTime());
        out.writeStringArray(new String[]{String.valueOf(this.getId()),String.valueOf(this.getValor()),this.getDecricao(),data,this.getCategoria().getNome()});
    }


}
