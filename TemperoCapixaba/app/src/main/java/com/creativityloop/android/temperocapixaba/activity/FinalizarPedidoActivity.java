package com.creativityloop.android.temperocapixaba.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.creativityloop.android.temperocapixaba.fragment.FinalizarPedidoFragment;

public class FinalizarPedidoActivity extends SingleFragmentActivity {

    public static final String EXTRA_PEDIDO_ID = "com.creativityloop.android.temperocapixaba.pedido_id";

    public static Intent newIntent(Context packageContext, long pedidoId) {
        Intent intent = new Intent(packageContext, FinalizarPedidoActivity.class);
        intent.putExtra(EXTRA_PEDIDO_ID, pedidoId);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        long pedidoId = (long) getIntent().getSerializableExtra(EXTRA_PEDIDO_ID);

        return FinalizarPedidoFragment.newInstance(pedidoId);
    }
}
