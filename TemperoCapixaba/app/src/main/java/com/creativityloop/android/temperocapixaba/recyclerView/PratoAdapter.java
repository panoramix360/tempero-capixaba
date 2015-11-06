package com.creativityloop.android.temperocapixaba.recyclerView;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Prato;

import java.util.List;

/**
 * Created by Guilherme on 05/11/2015.
 */
public class PratoAdapter extends RecyclerView.Adapter<PratoHolder> {
    private List<Prato> mPratos;
    private FragmentActivity mCurrentActivity;
    private List<ItemPedido> mItensPedido;

    public PratoAdapter(List<Prato> pratos, List<ItemPedido> itensPedido, FragmentActivity currentActivity) {
        mPratos = pratos;
        mItensPedido = itensPedido;
        mCurrentActivity = currentActivity;
    }

    @Override
    public PratoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCurrentActivity);
        View view = layoutInflater.inflate(R.layout.list_item_cardapio_recycler_view, parent, false);
        return new PratoHolder(view, mItensPedido);
    }

    @Override
    public void onBindViewHolder(PratoHolder holder, int position) {
        Prato prato = mPratos.get(position);
        holder.bindPrato(prato);
    }

    @Override
    public int getItemCount() {
        return mPratos.size();
    }
}