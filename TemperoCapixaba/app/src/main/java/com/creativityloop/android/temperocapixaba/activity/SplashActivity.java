package com.creativityloop.android.temperocapixaba.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import com.creativityloop.android.temperocapixaba.R;

public class SplashActivity extends Activity {

    /** tempo de duração **/
    private final int SPLASH_DISPLAY_LENGTH = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                SplashActivity.this.startActivity(MeusPedidosActivity.newIntent(getApplicationContext()));
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
