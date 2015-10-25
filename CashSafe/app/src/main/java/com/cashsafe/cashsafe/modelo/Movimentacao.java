package com.cashsafe.cashsafe.modelo;

import android.os.Parcelable;

import java.util.Calendar;

/**
 * Created by aelx on 24/10/15.
 */
public abstract class Movimentacao implements Parcelable {

    protected int id;
    protected double valor;
    protected String decricao;
    protected Calendar data;

    private Categoria categoria;

    public Movimentacao(int id, double valor, String decricao, Calendar data, Categoria categoria) {
        this.id = id;
        this.valor = valor;
        this.decricao = decricao;
        this.data = data;
        this.categoria = categoria;
    }

    public Movimentacao(Calendar data,Categoria categoria, String decricao, double valor) {
        this.data = data;
        this.categoria = categoria;
        this.decricao = decricao;
        this.valor = valor;
    }

    public Movimentacao(Calendar data, String decricao, double valor) {
        this.data = data;
        this.decricao = decricao;
        this.valor = valor;
    }

    public Movimentacao(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


}
