package com.cashsafe.cashsafe.modelo;

import java.util.Date;

/**
 * Created by hugo on 13/10/15.
 */
public class Despesa {

    private double valor;
    private String decricao;
    private String vencimento;
    private String pagamento;
    private String pago;
    private String fixa;
    private String categoria;

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

    public String getVencimento() {
        return vencimento;
    }

    public void setVencimento(String vencimento) {
        this.vencimento = vencimento;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public String getFixa() {
        return fixa;
    }

    public void setFixa(String fixa) {
        this.fixa = fixa;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
