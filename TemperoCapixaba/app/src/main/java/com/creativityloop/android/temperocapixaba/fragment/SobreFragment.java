package com.creativityloop.android.temperocapixaba.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creativityloop.android.temperocapixaba.R;

public class SobreFragment extends Fragment {

    public static SobreFragment newInstance() {
        Bundle args = new Bundle();

        SobreFragment fragment = new SobreFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sobre, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(R.string.sobre_title);

        return v;
    }
}
