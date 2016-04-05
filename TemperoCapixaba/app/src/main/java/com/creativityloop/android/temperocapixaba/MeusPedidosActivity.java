package com.creativityloop.android.temperocapixaba;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
