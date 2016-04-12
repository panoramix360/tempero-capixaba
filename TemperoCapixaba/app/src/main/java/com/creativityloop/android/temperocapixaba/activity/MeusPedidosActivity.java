package com.creativityloop.android.temperocapixaba.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.creativityloop.android.temperocapixaba.fragment.MeusPedidosFragment;

public class MeusPedidosActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MeusPedidosActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return MeusPedidosFragment.newInstance();
    }
}
