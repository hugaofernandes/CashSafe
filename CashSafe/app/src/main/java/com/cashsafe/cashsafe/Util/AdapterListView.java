package com.cashsafe.cashsafe.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.modelo.Despesa;
import com.cashsafe.cashsafe.modelo.Movimentacao;

import java.util.List;

/**
 * Created by aelx on 24/10/15.
 */
public class AdapterListView  extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Movimentacao> itens;

    public AdapterListView(Context context, List<Movimentacao> itens) {
        this.itens = itens;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return itens.size();
    }

    public Movimentacao getItem(int position) {
        return itens.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ItemSuporte itemHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_list, null);
            itemHolder = new ItemSuporte();
            itemHolder.descricao = ((TextView) view.findViewById(R.id.descricao));
            itemHolder.categoria = ((TextView) view.findViewById(R.id.categoria));
            itemHolder.valor = ((TextView) view.findViewById(R.id.valor));
            view.setTag(itemHolder);
        } else {

            itemHolder = (ItemSuporte) view.getTag();
        }

        Movimentacao item = itens.get(position);
        itemHolder.descricao.setText(item.getDecricao());
        itemHolder.categoria.setText(item.getCategoria().getNome());
        itemHolder.valor.setText(String.format("%.2f", item.getValor()));
        //retorna a view com as informações
        return view;
    }

    /**
     * Classe de suporte para os itens do layout.
     */
    private class ItemSuporte {
        TextView descricao;
        TextView categoria;
        TextView valor;
    }
}
