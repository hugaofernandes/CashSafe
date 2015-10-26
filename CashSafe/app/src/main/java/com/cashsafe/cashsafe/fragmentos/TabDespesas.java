package com.cashsafe.cashsafe.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cashsafe.cashsafe.DAO.CategoriaDespesaDAO;
import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.Util.CategoriaAdapterListView;
import com.cashsafe.cashsafe.atividades.DespesasCategoriaActivity;
import com.cashsafe.cashsafe.atividades.EditarCategoriaDespesaActivity;
import com.cashsafe.cashsafe.modelo.Categoria;
import com.cashsafe.cashsafe.modelo.CategoriaDespesa;
import com.cashsafe.cashsafe.modelo.Despesa;

import java.util.ArrayList;

public class TabDespesas extends Fragment {
    private View mView;
    private ListView listaCategorias;
    public TabDespesas() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mView= inflater.inflate(R.layout.fragment_tabdespesas, container, false);
        listaCategorias= (ListView)this.mView.findViewById(R.id.lista_categorias_despesas);
        listaCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                CategoriaDespesa categoriaSelecionada= (CategoriaDespesa) (listaCategorias.getItemAtPosition(myItemInt));
                editarCategoriaDespesa(categoriaSelecionada.getNome());
            }
        });
        this.atualizarData();
        return this.mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        this.atualizarData();
    }
    public void atualizarData(){
        CategoriaDespesaDAO categoriaDespesaDAO = new CategoriaDespesaDAO(this.getContext());
        ArrayList<Categoria> categoriasDespesa = (ArrayList<Categoria>) categoriaDespesaDAO.getTodasAsCategorias();
        listaCategorias.setAdapter(new CategoriaAdapterListView(this.getContext(), categoriasDespesa));
    }

    public void editarCategoriaDespesa(String nome){
        Intent intent = new Intent(getActivity(), EditarCategoriaDespesaActivity.class);
        intent.putExtra("nome_categoria",nome);
        startActivity(intent);
    }
}
