package com.cashsafe.cashsafe.modelo;

/**
 * Created by aelx on 24/10/15.
 */
public class CategoriaDespesa extends Categoria{

    public CategoriaDespesa() {
        this.setTipo(tipo_categorias.despesa);
    }
    public CategoriaDespesa(String nome) {
        super(nome);
        this.setTipo(tipo_categorias.despesa);

    }
}
