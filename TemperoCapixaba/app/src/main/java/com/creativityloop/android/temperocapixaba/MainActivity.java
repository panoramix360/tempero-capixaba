package com.creativityloop.android.temperocapixaba;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        //Limpar a base caso necessario
        //getBaseContext().deleteDatabase("temperoCapixaba.db");

        return new CardapioDiarioFragment();
    }
}
