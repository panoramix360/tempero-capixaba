package com.creativityloop.android.temperocapixaba.model;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class ItemPedido {

    public enum Tamanho {
        GRANDE, PEQUENO
    }

    private Prato mPrato;
    private Tamanho mTamanho;
    private int mQuantidade;

    public Prato getPrato() {
        return mPrato;
    }

    public void setPrato(Prato prato) {
        mPrato = prato;
    }

    public Tamanho getTamanho() {
        return mTamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        mTamanho = tamanho;
    }

    public int getQuantidade() {
        return mQuantidade;
    }

    public void setQuantidade(int quantidade) {
        mQuantidade = quantidade;
    }
}
