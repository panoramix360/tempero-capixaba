package com.creativityloop.android.temperocapixaba.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.creativityloop.android.temperocapixaba.fragment.MeusPedidosFragment;

/**
 * Created by LucasReis on 08/04/2016.
 */
public class PedidoDetalhesActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, PedidoDetalhesActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return MeusPedidosFragment.newInstance();
    }
}
