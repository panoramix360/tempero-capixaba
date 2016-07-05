package com.creativityloop.android.temperocapixaba.model;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Pedido extends SugarRecord {

    public int mId;
    public Usuario mUsuario;
    public String mEndereco;
    public String mData;
    public int mStatusCodigo;

    @Ignore
    public GregorianCalendar mDataObj;

    @Ignore
    public List<ItemPedido> mItensPedido;

    @Ignore
    public StatusPedido mStatus;

    public Pedido() {}

    public Pedido(int id, Usuario usuario, String endereco, String data, int mStatusCodigo) {
        this.mId = id;
        this.mUsuario = usuario;
        this.mEndereco = endereco;
        this.mData = data;
        this.mStatusCodigo = mStatusCodigo;
        this.mStatus = StatusPedido.values()[mStatusCodigo];
    }

    public List<ItemPedido> getItensPedido() {
        return mItensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        mItensPedido = itensPedido;
    }
}
