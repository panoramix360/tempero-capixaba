package com.creativityloop.android.temperocapixaba.model;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Pedido {

    private Usuario mUsuario;
    private String mEndereco;
    private GregorianCalendar mData;
    private List<ItemPedido> mRefeicoes;

    public Usuario getUsuario() {
        return mUsuario;
    }

    public void setUsuario(Usuario usuario) {
        mUsuario = usuario;
    }

    public String getEndereco() {
        return mEndereco;
    }

    public void setEndereco(String endereco) {
        mEndereco = endereco;
    }

    public GregorianCalendar getData() {
        return mData;
    }

    public void setData(GregorianCalendar data) {
        mData = data;
    }

    public List<ItemPedido> getRefeicoes() {
        return mRefeicoes;
    }

    public void setRefeicoes(List<ItemPedido> refeicoes) {
        this.mRefeicoes = refeicoes;
    }
}
