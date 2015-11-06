package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;

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
        return ItemPedido.find(ItemPedido.class, "id = ?", pedidoId + "");
    }

    public void saveItemPedido(ItemPedido itemPedido) {
        ItemPedido.save(itemPedido);
    }

    public void deleteItemPedido(long itemPedidoId) {
        ItemPedido itemPedido = ItemPedido.findById(ItemPedido.class, itemPedidoId);
        itemPedido.delete();
    }
}
