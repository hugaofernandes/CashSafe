package com.cashsafe.cashsafe.modelo;

import java.util.Date;

/**
 * Created by hugo on 13/10/15.
 */
public class Despesa {

    private double valor;
    private String decricao;
    private Date vencimento;
    private String metodoPagamento;
    private boolean pago;
    private boolean fixa;
    private String categoria;

    public Despesa(Date vencimento, boolean pago, String metodoPagamento, boolean fixa, String categoria, String decricao, double valor) {
        this.vencimento = vencimento;
        this.pago = pago;
        this.metodoPagamento = metodoPagamento;
        this.fixa = fixa;
        this.categoria = categoria;
        this.decricao = decricao;
        this.valor = valor;
    }
    public Despesa(){}

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

    public Date getVencimento() {
        return vencimento;
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public boolean isFixa() {
        return fixa;
    }

    public void setFixa(boolean fixa) {
        this.fixa = fixa;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
