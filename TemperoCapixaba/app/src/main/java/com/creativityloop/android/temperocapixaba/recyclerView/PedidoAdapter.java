package com.creativityloop.android.temperocapixaba.recyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.model.Pedido;

import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoHolder> {
    private Context mContext;
    private List<Pedido> mPedidos;

    public PedidoAdapter(Context currentContext, List<Pedido> pedidos) {
        mContext = currentContext;
        mPedidos = pedidos;
    }

    @Override
    public PedidoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.list_item_pedido_recycler_view, parent, false);
        return new PedidoHolder(view);
    }

    @Override
    public void onBindViewHolder(PedidoHolder holder, int position) {
        Pedido pedido = mPedidos.get(position);
        holder.bindPedido(pedido);
    }

    @Override
    public int getItemCount() {
        return mPedidos.size();
    }
}
