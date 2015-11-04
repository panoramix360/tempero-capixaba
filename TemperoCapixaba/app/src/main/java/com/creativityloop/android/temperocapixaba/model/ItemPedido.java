package com.creativityloop.android.temperocapixaba.model;

import com.orm.SugarRecord;

public class ItemPedido extends SugarRecord {

    public static enum Tamanho {
        GRANDE, PEQUENO;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public Pedido mPedido;
    public Prato mPrato;
    public Tamanho mTamanho;
    public int mQuantidade;

    public ItemPedido() {}

    public ItemPedido(Pedido pedido, Prato prato, Tamanho tamanho, int quantidade) {
        this.mPedido = pedido;
        this.mPrato = prato;
        this.mTamanho = tamanho;
        this.mQuantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemPedido itemPedido = (ItemPedido) o;

        return !(mPrato.mNome != null ? !mPrato.mNome.equals(itemPedido.mPrato.mNome) : itemPedido.mPrato.mNome != null);

    }
}
