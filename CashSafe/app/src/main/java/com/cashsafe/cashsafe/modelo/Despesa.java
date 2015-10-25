package com.cashsafe.cashsafe.modelo;

import java.util.Calendar;
import java.util.Date;

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

}
