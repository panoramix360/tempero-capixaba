package com.creativityloop.android.temperocapixaba.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.creativityloop.android.temperocapixaba.fragment.ResumoPedidoFragment;

public class ResumoPedidoActivity extends SingleFragmentActivity {

    public static final String EXTRA_PEDIDO_ID = "com.creativityloop.android.temperocapixaba.pedido_id";
    public static final String EXTRA_IS_DETALHES = "com.creativityloop.android.temperocapixaba.is_detalhes";

    public static Intent newIntent(Context packageContext, long pedidoId, boolean isDetalhes) {
        Intent intent = new Intent(packageContext, ResumoPedidoActivity.class);
        intent.putExtra(EXTRA_PEDIDO_ID, pedidoId);
        intent.putExtra(EXTRA_IS_DETALHES, isDetalhes);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        long pedidoId = (long) getIntent().getSerializableExtra(EXTRA_PEDIDO_ID);
        boolean isDetalhes = (boolean) getIntent().getSerializableExtra(EXTRA_IS_DETALHES);

        return ResumoPedidoFragment.newInstance(pedidoId, isDetalhes);
    }
}
