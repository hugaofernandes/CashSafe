package com.cashsafe.cashsafe.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hugo on 13/10/15.
 */
public class Despesa extends  Movimentacao{
    private String metodoPagamento;

    public Despesa(Calendar data, CategoriaDespesa categoria, String decricao, double valor, String metodoPagamento) {
        super(data, categoria, decricao, valor);
        this.metodoPagamento = metodoPagamento;
    }

    public Despesa(Calendar data, String decricao, double valor, String metodoPagamento) {
        super(data, decricao, valor);
        this.metodoPagamento = metodoPagamento;
    }

    public Despesa() {
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    private Despesa(Parcel in) {
        String[] dados= new String[6];
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
        this.setMetodoPagamento(dados[5]);

    }

    public static final Parcelable.Creator<Despesa> CREATOR = new Parcelable.Creator<Despesa>() {
        public Despesa createFromParcel(Parcel in) {
            return new Despesa(in);
        }

        public Despesa[] newArray(int size) {
            return new Despesa[size];
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
        out.writeStringArray(new String[]{String.valueOf(this.getId()),String.valueOf(this.getValor()),this.getDecricao(),data,this.getCategoria().getNome(),this.getMetodoPagamento()});
    }

}
