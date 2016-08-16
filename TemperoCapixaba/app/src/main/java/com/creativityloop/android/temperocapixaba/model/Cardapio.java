package com.creativityloop.android.temperocapixaba.model;

import java.util.Date;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Cardapio extends RealmObject {

    @PrimaryKey
    private int mId;
    private Date mData;
    private int mContador;

    @Ignore
    private List<Prato> mPratos;

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public List<Prato> getPratos() {
        return mPratos;
    }

    public void setPratos(List<Prato> mPratos) {
        this.mPratos = mPratos;
    }

    public Date getData() {
        return mData;
    }

    public void setData(Date mData) {
        this.mData = mData;
    }

    public int getContador() {
        return mContador;
    }

    public void setContador(int mContador) {
        this.mContador = mContador;
    }
}
