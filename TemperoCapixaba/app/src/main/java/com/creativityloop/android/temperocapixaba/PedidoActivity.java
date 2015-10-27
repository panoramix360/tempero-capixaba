package com.creativityloop.android.temperocapixaba;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class PedidoActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, PedidoActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return new PedidoFragment();
    }
}
