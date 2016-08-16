package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;

import java.util.GregorianCalendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class PedidoLab {
    private static PedidoLab sPedidoLab;

    public final static String URL_GET_CARDAPIO = "getCardapioAPI/";

    private Context mContext;
    private Realm realm;

    public static PedidoLab get(Context context) {
        if(sPedidoLab == null) {
            sPedidoLab = new PedidoLab(context);
        }

        return sPedidoLab;
    }

    private PedidoLab(Context context) {
        mContext = context.getApplicationContext();
        realm = Realm.getDefaultInstance();
    }

    public RealmList<Pedido> getPedidos() {
        RealmResults<Pedido> results = realm.where(Pedido.class).findAll();
        RealmList<Pedido> pedidos =  new RealmList<>();
        for(Pedido pedido : results) {
            pedidos.add(pedido);
        }
        return pedidos;
    }

    public Pedido getPedido(long pedidoId) {
        Pedido pedido = null;
        RealmQuery<Pedido> query = realm.where(Pedido.class).equalTo("mId", pedidoId);
        List<Pedido> pedidos = query.findAll();

        if(pedidos.size() > 0) {
            pedido = pedidos.get(0);
        }

        if(pedido != null) {
            RealmList<ItemPedido> itens = ItemPedidoLab.get(mContext).getItemPedidos(pedidoId);
            for(ItemPedido itemPedido : itens) {
               itemPedido.preencherPrato(mContext);
            }
        }
        return pedido;
    }

    public Pedido getPedido(GregorianCalendar data) {
        List<Pedido> pedidos = realm.where(Pedido.class).equalTo("mData", data + "").findAll();
        if(pedidos.isEmpty())
        {
            return null;
        }
        else
        {
            return pedidos.get(0);
        }
    }


    public int savePedido(final Pedido pedido) {
        realm.beginTransaction();
        int nextInt = realm.where(Pedido.class).findAll().size() + 1;
        Pedido pedidoToInsert = realm.createObject(Pedido.class);
        pedidoToInsert.setId(nextInt);
        pedidoToInsert.setUsuario(pedido.getUsuario());
        pedidoToInsert.setEndereco(pedido.getEndereco());
        pedidoToInsert.setData(pedido.getData());
        pedidoToInsert.setStatusByCodigo(pedido.getStatusCodigo());
        realm.commitTransaction();
        return nextInt;
    }

    public void deletePedido(long pedidoId) {
        final List<Pedido> pedidoToDelete = realm.where(Pedido.class).equalTo("mId", pedidoId).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                pedidoToDelete.get(0).deleteFromRealm();
            }
        });
    }
}
