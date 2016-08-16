package com.creativityloop.android.temperocapixaba.model;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.database.PratoLab;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class ItemPedido extends RealmObject {

    @PrimaryKey
    private int mId;
    private int mPratoId;
    private int mQuantidadePequena;
    private int mQuantidadeGrande;

    @Ignore
    private Prato mPrato;

    public void preencherPrato(Context context) {
        this.mPrato = PratoLab.get(context).getPrato(this.getPratoId());
    }

    @Override
    public String toString() {
        String itemPedidoStr = "";
        if(this.getQuantidadeGrande() > 0) {
            itemPedidoStr += this.getQuantidadeGrande() + "x Grande ";
        }

        if(this.getQuantidadePequena() > 0) {
            itemPedidoStr += this.getQuantidadePequena() + "x Pequena ";
        }

        if(this.getPrato() != null) {
            itemPedidoStr += "\n" + this.getPrato().getNome();
        }

        return itemPedidoStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPedido itemPedido = (ItemPedido) o;

        return !(mPrato.getNome() != null ? !mPrato.getNome().equals(itemPedido.getPrato().getNome()) : itemPedido.getPrato().getNome() != null);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getPratoId() {
        return mPratoId;
    }

    public void setPratoId(int pratoId) {
        mPratoId = pratoId;
    }

    public int getQuantidadePequena() {
        return mQuantidadePequena;
    }

    public void setQuantidadePequena(int quantidadePequena) {
        mQuantidadePequena = quantidadePequena;
    }

    public int getQuantidadeGrande() {
        return mQuantidadeGrande;
    }

    public void setQuantidadeGrande(int quantidadeGrande) {
        mQuantidadeGrande = quantidadeGrande;
    }

    public Prato getPrato() {
        return mPrato;
    }

    public void setPrato(Prato prato) {
        mPrato = prato;
    }
}
