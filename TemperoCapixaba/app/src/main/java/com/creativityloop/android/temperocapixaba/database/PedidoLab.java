package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;

import java.util.GregorianCalendar;
import java.util.List;

public class PedidoLab {
    private static PedidoLab sPedidoLab;

    public final static String URL_GET_CARDAPIO = "getCardapioAPI/";

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
        Pedido pedido = null;
        List<Pedido> pedidos = Pedido.find(Pedido.class, "m_id = ?", pedidoId + "");
        if(pedidos.size() == 0) {
            pedido = Pedido.findById(Pedido.class, pedidoId);
        } else {
            pedido = pedidos.get(0);
        }

        if(pedido != null) {
            pedido.setItensPedido(ItemPedidoLab.get(mContext).getItemPedidos(pedidoId));
            for(ItemPedido itemPedido : pedido.getItensPedido()) {
                itemPedido.preencherPrato(mContext);
            }
        }
        return pedido;
    }

    public Pedido getPedido(GregorianCalendar data) {
        List<Pedido> pedidos = Pedido.find(Pedido.class, "m_data = ?", data + "");
        if(pedidos == null || pedidos.isEmpty())
        {
            return null;
        }
        else
        {
            return pedidos.get(0);
        }
    }


    public void savePedido(Pedido pedido) {
        Pedido.save(pedido);
    }

    public void deletePedido(long pedidoId) {
        Pedido pedido = Pedido.findById(Pedido.class, pedidoId);
        pedido.delete();
    }
}
