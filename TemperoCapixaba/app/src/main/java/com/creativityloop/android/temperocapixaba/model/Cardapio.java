package com.creativityloop.android.temperocapixaba.model;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by LucasReis on 29/09/2015.
 */
public class Cardapio {

    private UUID mId;
    private List<Prato> mPratos;
    private GregorianCalendar mData;

    public List<Prato> getPratos() {
        return mPratos;
    }

    public void setPratos(List<Prato> pratos) {
        mPratos = pratos;
    }

    public GregorianCalendar getData() {
        return mData;
    }

    public void setData(GregorianCalendar data) {
        this.mData = data;
    }
}
