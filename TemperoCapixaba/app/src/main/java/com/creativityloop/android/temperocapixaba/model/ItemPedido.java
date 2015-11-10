package com.creativityloop.android.temperocapixaba.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class ItemPedido extends SugarRecord {

    public Pedido mPedido;
    public Prato mPrato;
    public int mQuantidadePequena;
    public int mQuantidadeGrande;

    @Ignore
    private boolean mChecked;

    public ItemPedido() {}

    public ItemPedido(Pedido pedido, Prato prato, int quantidadePequena, int quantidadeGrande) {
        this.mPedido = pedido;
        this.mPrato = prato;
        this.mQuantidadePequena = quantidadePequena;
        this.mQuantidadeGrande = quantidadeGrande;
    }

    public boolean ismChecked() {
        return mChecked;
    }

    public void setmChecked(boolean mChecked) {
        this.mChecked = mChecked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPedido itemPedido = (ItemPedido) o;

        return !(mPrato.mNome != null ? !mPrato.mNome.equals(itemPedido.mPrato.mNome) : itemPedido.mPrato.mNome != null);
    }
}
