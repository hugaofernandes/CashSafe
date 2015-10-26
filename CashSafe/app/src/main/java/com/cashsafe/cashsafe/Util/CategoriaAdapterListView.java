package com.cashsafe.cashsafe.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cashsafe.cashsafe.R;
import com.cashsafe.cashsafe.modelo.Categoria;

import java.util.List;

/**
 * Created by aelx on 25/10/15.
 */
public class CategoriaAdapterListView extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Categoria> itens;

    public CategoriaAdapterListView(Context context, List<Categoria> itens) {
        this.itens = itens;
        mInflater = LayoutInflater.from(context);
    }
    public int getCount() {
        return itens.size();
    }

    public Categoria getItem(int position) {
        return itens.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ItemSuporte itemHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_lista_categoria, null);
            itemHolder = new ItemSuporte();
            itemHolder.categoria_nome = ((TextView) view.findViewById(R.id.categoria_nome));
            view.setTag(itemHolder);
        } else {

            itemHolder = (ItemSuporte) view.getTag();
        }

        Categoria item = itens.get(position);
        itemHolder.categoria_nome.setText(item.getNome());
        return view;
    }

    private class ItemSuporte {
        TextView categoria_nome;
    }
}
