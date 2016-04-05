package com.creativityloop.android.temperocapixaba;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

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
        return CardapioDiarioFragment.newInstance();
    }
}
