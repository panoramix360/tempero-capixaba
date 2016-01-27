package com.creativityloop.android.temperocapixaba.model;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.database.PratoLab;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Mock {
    private static Mock sMock;

    private Context mContext;

    private List<Cardapio> mCardapios;

    public static Mock get(Context context) {
        if(sMock == null) {
            sMock = new Mock(context);
        }
        return sMock;
    }

    private Mock(Context context) {
        mContext = context;
        mCardapios = new ArrayList<>();

        initPratos();
        initCardapios();
    }

    private void initPratos() {
        for(int i = 0; i < 25; i++) {
            Prato prato = new Prato(i, "Prato #" + i);
            PratoLab.get(mContext).savePrato(prato);
        }
    }

    private void initCardapios() {
        for(int i = 0; i < 20; i++) {
            Cardapio cardapio = new Cardapio();
            cardapio.setPratos(PratoLab.get(mContext).getPratos());
            cardapio.setData(new GregorianCalendar());
            mCardapios.add(cardapio);
        }
    }

    public Cardapio getCardapioOfDay(GregorianCalendar data) {
        for(Cardapio cardapio : mCardapios) {
            if(cardapio.getData().get(Calendar.YEAR) == data.get(Calendar.YEAR)
                    && cardapio.getData().get(Calendar.MONTH) == data.get(Calendar.MONTH)
                    && cardapio.getData().get(Calendar.DAY_OF_MONTH) == data.get(Calendar.DAY_OF_MONTH)) {
                return cardapio;
            }
        }
        return null;
    }

    public List<Cardapio> getCardapios() {
        return mCardapios;
    }
}
