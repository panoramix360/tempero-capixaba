package com.creativityloop.android.temperocapixaba.model;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.database.PratoLab;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

public class ItemPedido extends SugarRecord {

    public Pedido mPedido;
    public int mPratoId;
    public int mQuantidadePequena;
    public int mQuantidadeGrande;

    @Ignore
    public Prato mPrato;

    @Ignore
    private boolean mChecked;

    public ItemPedido() {}

    public ItemPedido(Pedido pedido, int pratoId, int quantidadePequena, int quantidadeGrande) {
        this.mPedido = pedido;
        this.mPratoId = pratoId;
        this.mQuantidadePequena = quantidadePequena;
        this.mQuantidadeGrande = quantidadeGrande;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        mChecked = checked;
    }

    public Prato getPrato() {
        return mPrato;
    }

    public void setPrato(Prato prato) {
        mPrato = prato;
        mPratoId = prato.getId();
    }

    public void preencherPrato(Context context) {
        this.mPrato = PratoLab.get(context).getPrato(mPratoId);
    }

    @Override
    public String toString() {
        return "- " + mQuantidadeGrande + "x " + mPrato.getNome() + "\n - " + mQuantidadePequena + "x " + mPrato.mNome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPedido itemPedido = (ItemPedido) o;

        return !(mPrato.mNome != null ? !mPrato.mNome.equals(itemPedido.mPrato.mNome) : itemPedido.mPrato.mNome != null);
    }
}
