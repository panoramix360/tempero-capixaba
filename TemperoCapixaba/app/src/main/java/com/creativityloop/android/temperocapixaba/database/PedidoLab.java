package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.creativityloop.android.temperocapixaba.model.Pedido;

import java.util.List;

public class PedidoLab {
    private static PedidoLab sPedidoLab;

    private Context mContext;

    public static PedidoLab get(Context context) {
        if(sPedidoLab == null) {
            sPedidoLab = new PedidoLab(context);
        }

        return sPedidoLab;
    }

    private PedidoLab(Context context) {
        mContext = context.getApplicationContext();
    }

    public List<Pedido> getPedidos() {
        return Pedido.listAll(Pedido.class);
    }

    public Pedido getPedido(long pedidoId) {
        return Pedido.findById(Pedido.class, pedidoId);
    }

    public void savePedido(Pedido pedido) {
        Pedido.save(pedido);
    }

    public void deletePedido(long pedidoId) {
        Pedido pedido = Pedido.findById(Pedido.class, pedidoId);
        pedido.delete();
    }
}
