package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.model.Prato;

import java.util.ArrayList;
import java.util.List;

public class PratoLab {
    private static PratoLab sPratoLab;

    private List<Prato> mPratos;

    private Context mContext;

    public static PratoLab get(Context context) {
        if(sPratoLab == null) {
            sPratoLab = new PratoLab(context);
        }

        return sPratoLab;
    }

    private PratoLab(Context context) {
        mContext = context.getApplicationContext();
        mPratos = new ArrayList<>();
    }

    public void clearPratos() {
        mPratos = new ArrayList<Prato>();
    }

    public List<Prato> getPratos() {
        return mPratos;
    }

    public Prato getPrato(int pratoId) {
        for(Prato prato : mPratos) {
            if(prato.getId() == pratoId) {
                return prato;
            }
        }

        return null;
    }

    public void savePrato(Prato prato) {
        mPratos.add(prato);
    }
}
