package com.creativityloop.android.temperocapixaba.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

        mPhoneImageButton = (ImageButton) v.findViewById(R.id.phone_image_button);
        mPhoneImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String phone = "+21988048445";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });

        mMarkerImageButton = (ImageButton) v.findViewById(R.id.marker_image_button);
        mMarkerImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = "Tempero Capixaba";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q=-22.9075208,-43.1778019 (" + name + ")"));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
        });
        return v;
    }
}
