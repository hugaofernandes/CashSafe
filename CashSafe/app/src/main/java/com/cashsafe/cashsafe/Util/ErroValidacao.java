package com.cashsafe.cashsafe.Util;

/**
 * Created by aelx on 15/11/15.
 */
public enum ErroValidacao {
    DATA_INVALIDA(0, "Data invalida"),
    VALOR_INVALIDO(1, "Valor invalido"),
    CAMPO_OBRIGATORIO(2, "Campo obrigatorio");

    private final int codigo;
    private final String descricao;

    private ErroValidacao(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return codigo + ": " + descricao;
    }
}
