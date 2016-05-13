package com.creativityloop.android.temperocapixaba.model;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.database.PratoLab;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class ItemPedido extends SugarRecord {

    public long mPedidoId;
    public int mPratoId;
    public int mQuantidadePequena;
    public int mQuantidadeGrande;

    @Ignore
    public Prato mPrato;
    @Ignore
    private Pedido mPedido;

    public ItemPedido() {}

    public ItemPedido(int pedidoId, int pratoId, int quantidadePequena, int quantidadeGrande) {
        this.mPedidoId = pedidoId;
        this.mPratoId = pratoId;
        this.mQuantidadePequena = quantidadePequena;
        this.mQuantidadeGrande = quantidadeGrande;
    }

    public Prato getPrato() {
        return mPrato;
    }

    public void setPrato(Prato prato) {
        mPrato = prato;
        mPratoId = prato.getId();
    }

    public Pedido getPedido() {
        return mPedido;
    }

    public void setPedido(Pedido pedido) {
        this.mPedido = pedido;
    }

    public void preencherPrato(Context context) {
        this.mPrato = PratoLab.get(context).getPrato(mPratoId);
    }

    @Override
    public String toString() {
        String itemPedidoStr = "";
        if(mQuantidadeGrande > 0) {
            itemPedidoStr += mQuantidadeGrande + "x Grande ";
        }

        if(mQuantidadePequena > 0) {
            itemPedidoStr += mQuantidadePequena + "x Pequena ";
        }

        itemPedidoStr += "\n" + mPrato.getNome();

        return itemPedidoStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPedido itemPedido = (ItemPedido) o;

        return !(mPrato.mNome != null ? !mPrato.mNome.equals(itemPedido.mPrato.mNome) : itemPedido.mPrato.mNome != null);
    }
}
