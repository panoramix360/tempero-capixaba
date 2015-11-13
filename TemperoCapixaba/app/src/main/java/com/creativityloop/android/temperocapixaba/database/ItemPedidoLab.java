package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.model.Cardapio;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Prato;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class ItemPedidoLab {
    private static ItemPedidoLab sItemPedidoLab;

    private Context mContext;

    public static ItemPedidoLab get(Context context) {
        if(sItemPedidoLab == null) {
            sItemPedidoLab = new ItemPedidoLab(context);
        }

        return sItemPedidoLab;
    }

    private ItemPedidoLab(Context context) {
        mContext = context.getApplicationContext();
    }

    public List<ItemPedido> getItemPedidos(long pedidoId) {
        return ItemPedido.find(ItemPedido.class, "m_pedido = ?", String.valueOf((pedidoId)));
    }
    
    public void saveItemPedido(ItemPedido itemPedido) {
        ItemPedido.save(itemPedido);
    }

    public void deleteItemPedido(long itemPedidoId) {
        ItemPedido itemPedido = ItemPedido.findById(ItemPedido.class, itemPedidoId);
        itemPedido.delete();
    }
    public List<ItemPedido> createItensPedidoComCardapio(Cardapio cardapio) {
        List<ItemPedido> itemPedidos = new ArrayList<>();
        for(Prato prato : cardapio.getPratos()) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.mPrato = prato;
            itemPedidos.add(itemPedido);
        }
        return itemPedidos;
    }

}
