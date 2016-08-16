package com.creativityloop.android.temperocapixaba.database;

import android.content.Context;

import com.creativityloop.android.temperocapixaba.model.Cardapio;
import com.creativityloop.android.temperocapixaba.model.ItemPedido;
import com.creativityloop.android.temperocapixaba.model.Pedido;
import com.creativityloop.android.temperocapixaba.model.Prato;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class ItemPedidoLab {
    private static ItemPedidoLab sItemPedidoLab;

    private Context mContext;
    private Realm realm;

    public static ItemPedidoLab get(Context context) {
        if(sItemPedidoLab == null) {
            sItemPedidoLab = new ItemPedidoLab(context);
        }

        return sItemPedidoLab;
    }

    private ItemPedidoLab(Context context) {
        mContext = context.getApplicationContext();
        realm = Realm.getDefaultInstance();
    }

    public RealmList<ItemPedido> getItemPedidos(long pedidoId) {
        Pedido pedido = realm.where(Pedido.class).equalTo("mId", pedidoId).findFirst();
        RealmList<ItemPedido> itensPedido =  new RealmList<>();
        for(ItemPedido itemPedido : pedido.getItensPedido()) {
            itensPedido.add(itemPedido);
        }
        return itensPedido;
    }
    
    public void saveItemPedidoByPedidoId(int pedidoId, ItemPedido itemPedido) {
        realm.beginTransaction();
        int nextInt = realm.where(ItemPedido.class).findAll().size() + 1;
        ItemPedido itemPedidoToInsert = realm.createObject(ItemPedido.class);
        itemPedidoToInsert.setId(nextInt);
        itemPedidoToInsert.setPratoId(itemPedido.getPratoId());
        itemPedidoToInsert.setQuantidadePequena(itemPedido.getQuantidadePequena());
        itemPedidoToInsert.setQuantidadeGrande(itemPedido.getQuantidadeGrande());

        Pedido pedido = realm.where(Pedido.class).equalTo("mId", pedidoId).findFirst();
        pedido.getItensPedido().add(itemPedidoToInsert);
        realm.commitTransaction();
    }

    public void deleteItemPedido(long itemPedidoId) {
        final List<ItemPedido> itemPedidoToDelete = realm.where(ItemPedido.class).equalTo("mId", itemPedidoId).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                itemPedidoToDelete.get(0).deleteFromRealm();
            }
        });
    }

    public List<ItemPedido> createItensPedidoComCardapio(Cardapio cardapio) {
        List<ItemPedido> itemPedidos = new ArrayList<>();
        for(Prato prato : cardapio.getPratos()) {
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPrato(prato);
            itemPedido.setPratoId(prato.getId());
            itemPedidos.add(itemPedido);
        }
        return itemPedidos;
    }

}
