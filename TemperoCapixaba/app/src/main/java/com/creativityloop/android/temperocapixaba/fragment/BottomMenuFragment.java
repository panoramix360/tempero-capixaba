package com.creativityloop.android.temperocapixaba.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.creativityloop.android.temperocapixaba.R;
import com.creativityloop.android.temperocapixaba.activity.MeusPedidosActivity;

/**
 * Created by lucasreis3000 on 04/05/16.
 */
public class BottomMenuFragment extends Fragment {

    private ImageButton mHomeImageButton;
    private ImageButton mPhoneImageButton;
    private ImageButton mMarkerImageButton;

    public static BottomMenuFragment newInstance() {
        BottomMenuFragment fragment = new BottomMenuFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bottom_menu, container, false);

        mHomeImageButton = (ImageButton) v.findViewById(R.id.home_image_button);
        mHomeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MeusPedidosActivity.newIntent(getContext());
                startActivity(intent);
            }
        });

        return v;
    }
}
