package com.creativityloop.android.temperocapixaba;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class SobreActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SobreActivity.class);
        return intent;
    }

    @Override
    public Fragment createFragment() {
        return SobreFragment.newInstance();
    }
}
