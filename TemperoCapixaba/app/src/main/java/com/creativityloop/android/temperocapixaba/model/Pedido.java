package com.creativityloop.android.temperocapixaba.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Pedido extends RealmObject {

    @PrimaryKey
    private int mId;
    private Usuario mUsuario;
    private String mEndereco;
    private String mData;
    private int mStatusCodigo;
    private RealmList<ItemPedido> mItensPedido;

    @Ignore
    public StatusPedido mStatus;

    public void setStatusByCodigo(int statusCodigo) {
        this.mStatusCodigo = statusCodigo;
        this.mStatus = StatusPedido.values()[statusCodigo];
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

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

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }

    public int getStatusCodigo() {
        return mStatusCodigo;
    }

    public void setStatusCodigo(int statusCodigo) {
        mStatusCodigo = statusCodigo;
    }

    public RealmList<ItemPedido> getItensPedido() {
        return mItensPedido;
    }

    public void setItensPedido(RealmList<ItemPedido> itensPedido) {
        mItensPedido = itensPedido;
    }

    public StatusPedido getStatus() {
        return mStatus;
    }

    public void setStatus(StatusPedido status) {
        mStatus = status;
    }
}
