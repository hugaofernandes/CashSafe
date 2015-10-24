package com.cashsafe.cashsafe.modelo;

/**
 * Created by aelx on 23/10/15.
 */
public abstract class Categoria {
    private String nome;
    public enum tipo_categorias{despesa,receita};

    private tipo_categorias tipo;

    public Categoria() {}

    public Categoria(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public tipo_categorias getTipo() {
        return tipo;
    }

    protected void setTipo(tipo_categorias tipo) {
        this.tipo = tipo;
    }

}
