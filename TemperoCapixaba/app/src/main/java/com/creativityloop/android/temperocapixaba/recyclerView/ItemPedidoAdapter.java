package com.creativityloop.android.temperocapixaba.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;

import java.util.List;

/**
 * Created by Guilherme on 05/11/2015.
 */
public class ItemPedidoAdapter extends RecyclerView.Adapter<ItemPedidoHolder> {
    private Context mContext;
    private List<ItemPedido> mItensPedido;

    public ItemPedidoAdapter(Context currentContext, List<ItemPedido> itensPedido) {
        mContext = currentContext;
        mItensPedido = itensPedido;
    }

    @Override
    public ItemPedidoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_cardapio_recycler_view, parent, false);
        return new ItemPedidoHolder(view, mContext);
    }

    @Override
    public void onBindViewHolder(ItemPedidoHolder holder, int position) {
        ItemPedido itemPedido = mItensPedido.get(position);
        itemPedido.preencherPrato(mContext);
        holder.bindItemPedido(itemPedido);
    }

    @Override
    public int getItemCount() {
        return mItensPedido.size();
    }
}