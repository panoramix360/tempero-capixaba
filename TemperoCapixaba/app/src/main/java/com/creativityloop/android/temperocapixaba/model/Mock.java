package com.creativityloop.android.temperocapixaba.model;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Mock {
    private static Mock sMock;

    private List<Cardapio> mCardapios;
    private List<Prato> mPratos;

    public static Mock get(Context context) {
        if(sMock == null) {
            sMock = new Mock(context);
        }
        return sMock;
    }

    private Mock(Context context) {
        mCardapios = new ArrayList<>();
        mPratos = new ArrayList<>();

        initPratos();
        initCardapios();
    }

    private void initPratos() {
        for(int i = 0; i < 5; i++) {
            Prato prato = new Prato();
            prato.setNome("Prato #" + i);
            mPratos.add(prato);
        }
    }

    private void initCardapios() {
        GregorianCalendar dataInicio = new GregorianCalendar(2015, 9, 28);
        for(int i = 0; i < 8; i++, dataInicio.add(Calendar.DAY_OF_MONTH, 1)) {
            Cardapio cardapio = new Cardapio();
            cardapio.setPratos(mPratos);
            cardapio.setData(dataInicio);
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

    public static String format(GregorianCalendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        fmt.setCalendar(calendar);
        return fmt.format(calendar.getTime());
    }
}
