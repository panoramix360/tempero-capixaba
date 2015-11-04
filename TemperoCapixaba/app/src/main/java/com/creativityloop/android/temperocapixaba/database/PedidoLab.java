package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.creativityloop.android.temperocapixaba.model.Pedido;

import java.util.List;

/**
 * Created by LucasReis on 29/10/2015.
 */
public class PedidoLab {
    private static PedidoLab sPedidoLab;

    private Context mContext;
    private List<Pedido> mPedidos;

    public static PedidoLab get(Context context) {
        if(sPedidoLab == null) {
            sPedidoLab = new PedidoLab(context);
        }

        return sPedidoLab;
    }

    private PedidoLab(Context context) {
        mContext = context.getApplicationContext();
        mPedidos = Pedido.listAll(Pedido.class);
    }

    public Pedido getPedido(long pedidoId) {
        for(Pedido pedido : mPedidos) {
            if(pedido.getId() == pedidoId) {
                return pedido;
            }
        }
        return null;
    }

    public void savePedido(Pedido pedido) {
        Pedido.save(pedido);
    }

    public void deletePedido(long pedidoId) {
        Pedido pedido = Pedido.findById(Pedido.class, pedidoId);
        pedido.delete();
    }
}
