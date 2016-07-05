package com.creativityloop.android.temperocapixaba.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.creativityloop.android.temperocapixaba.fragment.CardapioDiarioFragment;

/**
 * Created by LucasReis on 05/04/2016.
 */
public class CardapioDiarioActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, CardapioDiarioActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        getSupportActionBar().setHomeButtonEnabled(false);
        return CardapioDiarioFragment.newInstance();
    }
}
