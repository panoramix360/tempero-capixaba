package com.creativityloop.android.temperocapixaba;

import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CardapioDiarioFragment();
    }
}
