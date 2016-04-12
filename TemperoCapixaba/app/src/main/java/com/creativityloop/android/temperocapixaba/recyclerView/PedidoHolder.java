package com.creativityloop.android.temperocapixaba.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;

/**
 * Created by LucasReis on 29/03/2016.
 */
public class PedidoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Pedido mPedido;

    // UI
    private TextView mPedidoTextView;
    private TextView mTotalTextView;

    public PedidoHolder(View itemView) {
        super(itemView);

        mPedidoTextView = (TextView) itemView.findViewById(R.id.pedido_text_view);
        mTotalTextView = (TextView) itemView.findViewById(R.id.total_text_view);
    }

    public void bindPedido(Pedido pedido) {
        mPedido = pedido;

        mPedidoTextView.setText("Pedido " + pedido.mId);

        int total = 0;
        for(ItemPedido itemPedido : pedido.mItensPedido) {
            total += itemPedido.mQuantidadePequena;
            total += itemPedido.mQuantidadeGrande;
        }

        mTotalTextView.setText(total + " comida(s)");
    }

    @Override
    public void onClick(View v) {

    }
}
