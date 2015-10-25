package com.cashsafe.cashsafe.modelo;

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
}
