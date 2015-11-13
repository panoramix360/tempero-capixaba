package com.creativityloop.android.temperocapixaba.model;

import com.orm.SugarRecord;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Pedido extends SugarRecord {

    public Usuario mUsuario;
    public String mEndereco;
    public GregorianCalendar mData;

    public Pedido() {}

    public Pedido(Usuario usuario, String endereco, GregorianCalendar data) {
        this.mUsuario = usuario;
        this.mEndereco = endereco;
        this.mData = data;
    }
}
