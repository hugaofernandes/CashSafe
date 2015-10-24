package com.cashsafe.cashsafe.modelo;

/**
 * Created by aelx on 24/10/15.
 */
public class CategoriaReceita extends Categoria{
    public CategoriaReceita() {
        this.setTipo(tipo_categorias.receita);
    }
    public CategoriaReceita(String nome) {
        super(nome);
        this.setTipo(Categoria.tipo_categorias.receita);

    }
}
