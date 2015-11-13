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
        GregorianCalendar dataInicio = new GregorianCalendar(2015, 9, 28);
        for(int i = 0; i < 20; i++, dataInicio.add(Calendar.DAY_OF_MONTH, 1)) {
            Cardapio cardapio = new Cardapio();
            cardapio.setPratos(PratoLab.get(mContext).getPratos());
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

    public List<ItemPedido> createItensPedidoComCardapio(Cardapio cardapio) {
        List<ItemPedido> itemPedidos = new ArrayList<>();
        for(Prato prato : cardapio.getPratos()) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPrato(prato);
            itemPedidos.add(itemPedido);
        }
        return itemPedidos;
    }

    public static String format(GregorianCalendar calendar){
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        fmt.setCalendar(calendar);
        return fmt.format(calendar.getTime());
    }
}
