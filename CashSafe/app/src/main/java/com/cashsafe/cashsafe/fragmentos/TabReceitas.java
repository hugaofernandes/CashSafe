package com.cashsafe.cashsafe.fragmentos;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cashsafe.cashsafe.DAO.CategoriaReceitaDAO;
import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.Util.CategoriaAdapterListView;
import com.cashsafe.cashsafe.atividades.EditarCategoriaReceitaActivity;
import com.cashsafe.cashsafe.modelo.Categoria;
import com.cashsafe.cashsafe.modelo.CategoriaDespesa;
import com.cashsafe.cashsafe.modelo.CategoriaReceita;

import java.util.ArrayList;

public class TabReceitas extends Fragment {
    private View mView;
    private ListView listaCategorias;
    public TabReceitas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mView= inflater.inflate(R.layout.fragment_tab_receitas, container, false);
        listaCategorias= (ListView)this.mView.findViewById(R.id.lista_categorias_receitas);
        listaCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                CategoriaReceita categoriaSelecionada= (CategoriaReceita) (listaCategorias.getItemAtPosition(myItemInt));
                editarCategoriaReceita(categoriaSelecionada.getNome());
            }
        });
        this.atualizarData();
        return this.mView;
    }
    @Override
    public void onResume() {
        super.onResume();
        this.atualizarData();
    }
    public void atualizarData(){
        CategoriaReceitaDAO categoriaDespesaDAO = new CategoriaReceitaDAO(this.getContext());
        ArrayList<Categoria> categoriasDespesa = (ArrayList<Categoria>) categoriaDespesaDAO.getTodasAsCategorias();
        listaCategorias.setAdapter(new CategoriaAdapterListView(this.getContext(), categoriasDespesa));
    }
    public void editarCategoriaReceita(String nome){
        Intent intent = new Intent(getActivity(), EditarCategoriaReceitaActivity.class);
        intent.putExtra("nome_categoria",nome);
        startActivity(intent);
    }
}
